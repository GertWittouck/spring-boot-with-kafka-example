package com.gwi.kafka.producer.book.order.model;

public record BookOrderItemDto(
        String isbn,
        int quantity
) {
}
