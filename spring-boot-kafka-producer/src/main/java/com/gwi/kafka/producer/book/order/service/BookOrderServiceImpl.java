package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.producer.book.order.constants.BookCategory;
import com.gwi.kafka.producer.book.order.entities.BookOrder;
import com.gwi.kafka.producer.book.order.entities.BookOrderItem;
import com.gwi.kafka.producer.book.order.exceptions.BookNotFoundException;
import com.gwi.kafka.producer.book.order.exceptions.MissingBookOrderDataException;
import com.gwi.kafka.producer.book.order.exceptions.NoBookOrderItemsException;
import com.gwi.kafka.messages.BookOrderMessage;
import com.gwi.kafka.messages.BookOrderItemMessage;
import com.gwi.kafka.producer.book.order.model.Books;
import com.gwi.kafka.producer.book.order.repository.BookOrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Service
public class BookOrderServiceImpl implements BookOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderServiceImpl.class);

    private final WebClient bookServiceWebClient;
    private final BookOrderRepository bookOrderRepository;
    private final MessageProducer<BookOrderMessage> messageProducer;

    @Value("${book.order.placed.topic}")
    private String bookOrderPlacedTopic;

    public BookOrderServiceImpl(
            @Qualifier("bookServiceWebClient") WebClient bookServiceWebClient,
            BookOrderRepository bookOrderRepository,
            MessageProducer<BookOrderMessage> messageProducer) {
        this.bookServiceWebClient = bookServiceWebClient;
        this.bookOrderRepository = bookOrderRepository;
        this.messageProducer = messageProducer;
    }

    @Override
    @Transactional
    public BookOrder placeOrder(BookOrderMessage bookOrder) {
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

        BookOrder placedBookOrder = bookOrderRepository.save(bookOrderToPlace.withBookOrderItems(bookOrderItems));
        LOGGER.info("Placed the order {} at {}", placedBookOrder, LocalDateTime.now());

        // Produce message indicating order has been placed and invoice should be created
        messageProducer.sendMessage(bookOrderPlacedTopic, bookOrder);

        return placedBookOrder;
    }

    private BookOrderItem convertBookOrderItemDto(BookOrderItemMessage bookOrderItemMessage, BookOrder bookOrder) {
        // Find book - if not found, throw an exception indication book is not in stock
        var books = bookServiceWebClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/book")
                                .pathSegment(bookOrderItemMessage.isbn())
                                .build())
                .retrieve()
                .bodyToMono(Books.class)
                .onErrorMap(BookNotFoundException.class, exception -> new BookNotFoundException(bookOrderItemMessage.isbn()))
                .block();

        // If response isn't returning a single book throw an exception
        if (books == null || books.books().size() != 1) {
            throw new BookNotFoundException(bookOrderItemMessage.isbn());
        }

        var book = books.books().iterator().next();

        return BookOrderItem.builder()
                .bookOrder(bookOrder)
                .bookTitle(book.title())
                .isbn(book.isbn())
                .category(BookCategory.UNKNOWN)
                .note(book.description())
                .quantity(bookOrderItemMessage.quantity())
                .build();
    }
}
