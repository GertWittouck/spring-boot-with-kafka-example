package com.gwi.kafka.consumer.book.resource;

import com.gwi.kafka.consumer.book.entities.Book;
import com.gwi.kafka.consumer.book.resource.model.BookItemResponse;
import com.gwi.kafka.consumer.book.resource.model.BookRequest;
import com.gwi.kafka.consumer.book.resource.model.BookResponse;
import com.gwi.kafka.consumer.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/v1/book")
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<BookResponse> getAll() {
        logger.info("Request received to find all available books.");
        var books = bookService.getAll();
        logger.info("Found {} books", books.isEmpty() ? 0 : books.size());
        return ResponseEntity.ok(BookResponse.of(books));
    }

    @PutMapping
    public ResponseEntity<BookItemResponse> add(@RequestBody BookRequest bookRequest) {
        logger.info("Request received to create a new book for {}", bookRequest);
        var newBook = Book.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .isbn(bookRequest.isbn())
                .description(bookRequest.description())
                .priceBeforeTax(ofNullable(bookRequest.priceBeforeTax()).orElse(ZERO.setScale(2, HALF_UP)))
                .build();

        var book = bookService.create(newBook);
        logger.info("Created a new book {}", book);
        return ResponseEntity.ok(BookItemResponse.of(book));
    }
}
