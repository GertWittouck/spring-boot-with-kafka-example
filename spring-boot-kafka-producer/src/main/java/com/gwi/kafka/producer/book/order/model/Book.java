package com.gwi.kafka.producer.book.order.model;

public record Book(
        String title,
        String author,
        String isbn,
        String description) {
}
