package com.gwi.kafka.consumer.invoice.service;

import com.gwi.kafka.consumer.book.entities.Book;
import com.gwi.kafka.consumer.book.exceptions.BookNotFoundException;
import com.gwi.kafka.consumer.book.service.BookService;
import com.gwi.kafka.consumer.invoice.book.order.BookOrder;
import com.gwi.kafka.consumer.invoice.book.order.BookOrderItem;
import com.gwi.kafka.consumer.invoice.entities.Invoice;
import com.gwi.kafka.messages.BookOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookOrderConsumerService implements MessageConsumer<BookOrderMessage, Invoice> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderConsumerService.class);

    private final BookService bookService;
    private final InvoiceService<BookOrder, Invoice> invoiceService;

    public BookOrderConsumerService(BookService bookService, InvoiceService<BookOrder, Invoice> invoiceService) {
        this.bookService = bookService;
        this.invoiceService = invoiceService;
    }

    @Override
    @KafkaListener(topics = "${book.order.placed.topic}", groupId = "${book.order.placed.group-id}")
    public Invoice consume(BookOrderMessage bookOrderMessage) {
        if (CollectionUtils.isEmpty(bookOrderMessage.bookOrderItems())) {
            LOGGER.warn("No book order items where found");
            return invoiceService.invoice(new BookOrder(List.of()));
        }

        var bookOrderItems = bookOrderMessage.bookOrderItems().stream()
                .map(message -> BookOrderItem.of(book(message.isbn()), message.quantity()))
                .toList();

        LOGGER.info("Found {} book order items to invoice.", bookOrderItems.size());

        return invoiceService.invoice(new BookOrder(bookOrderItems));
    }

    private Book book(String isbn) {
        var availableBook = bookService.getByIsbn(isbn);
        return availableBook
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }
}
