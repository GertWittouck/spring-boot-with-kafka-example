package com.gwi.kafka.consumer.book.resource.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookRequest(
        @NotNull String title,
        @NotNull String author,
        @NotNull String isbn,
        String description,
        BigDecimal priceBeforeTax) {
}
