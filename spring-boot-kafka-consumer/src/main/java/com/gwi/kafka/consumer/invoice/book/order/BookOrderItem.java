package com.gwi.kafka.consumer.invoice.book.order;

import com.gwi.kafka.consumer.book.entities.Book;

public record BookOrderItem(
        Book book,
        int quantity) {

    public static BookOrderItem of(Book book, int quantity) {
        return new BookOrderItem(book, quantity);
    }
}
