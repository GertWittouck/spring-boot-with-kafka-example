package com.gwi.kafka.producer.book.order.resource.model;

import jakarta.validation.constraints.NotNull;

public record BookOrderItemRequest(
        @NotNull String isbn,
        @NotNull int quantity) {
}
