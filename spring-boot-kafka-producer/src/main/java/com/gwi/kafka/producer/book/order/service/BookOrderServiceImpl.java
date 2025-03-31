package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.producer.book.order.constants.BookCategory;
import com.gwi.kafka.producer.book.order.entities.BookOrder;
import com.gwi.kafka.producer.book.order.entities.BookOrderItem;
import com.gwi.kafka.producer.book.order.exceptions.BookNotFoundException;
import com.gwi.kafka.producer.book.order.exceptions.MissingBookOrderDataException;
import com.gwi.kafka.producer.book.order.exceptions.NoBookOrderItemsException;
import com.gwi.kafka.producer.book.order.model.BookOrderDto;
import com.gwi.kafka.producer.book.order.model.BookOrderItemDto;
import com.gwi.kafka.producer.book.order.model.Books;
import com.gwi.kafka.producer.book.order.repository.BookOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Service
public class BookOrderServiceImpl implements BookOrderService {

    private final WebClient bookServiceWebClient;
    private final BookOrderRepository bookOrderRepository;

    public BookOrderServiceImpl(@Qualifier("bookServiceWebClient") WebClient bookServiceWebClient, BookOrderRepository bookOrderRepository) {
        this.bookServiceWebClient = bookServiceWebClient;
        this.bookOrderRepository = bookOrderRepository;
    }

    @Override
    public BookOrder placeOrder(BookOrderDto bookOrder) {
        /*
            1. Create book order -- DONE
            2. Persist in database -- DONE
            3. Produce message indicating order is placed -- TODO
         */

        if (ofNullable(bookOrder).isEmpty()) {
            throw new MissingBookOrderDataException(LocalDateTime.now());
        }

        // If the order has no line items - throw an exception indication the order is incomplete
        if (bookOrder.bookOrderItems().isEmpty()) {
            throw new NoBookOrderItemsException(LocalDateTime.now());
        }

        BookOrder bookOrderToPlace = new BookOrder();

        var bookOrderItems = bookOrder.bookOrderItems().stream()
                .map(orderItemDto -> convertBookOrderItemDto(orderItemDto, bookOrderToPlace))
                .toList();

        return bookOrderRepository.save(bookOrderToPlace.withBookOrderItems(bookOrderItems));
    }

    private BookOrderItem convertBookOrderItemDto(BookOrderItemDto bookOrderItemDto, BookOrder bookOrder) {
        // Find book - if not found, throw an exception indication book is not in stock
        var books = bookServiceWebClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/book")
                                .pathSegment(bookOrderItemDto.isbn())
                                .build())
                .retrieve()
                .bodyToMono(Books.class)
                .onErrorMap(BookNotFoundException.class, exception -> new BookNotFoundException(bookOrderItemDto.isbn()))
                .block();

        // If response isn't returning a single book throw an exception
        if (books == null || books.books().size() != 1) {
            throw new BookNotFoundException(bookOrderItemDto.isbn());
        }

        var book = books.books().iterator().next();

        return BookOrderItem.builder()
                .bookOrder(bookOrder)
                .bookTitle(book.title())
                .isbn(book.isbn())
                .category(BookCategory.UNKNOWN)
                .note(book.description())
                .quantity(bookOrderItemDto.quantity())
                .build();
    }
}
