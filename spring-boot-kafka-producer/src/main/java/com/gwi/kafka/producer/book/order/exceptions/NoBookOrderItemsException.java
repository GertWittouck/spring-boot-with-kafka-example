package com.gwi.kafka.producer.book.order.exceptions;

import java.time.LocalDateTime;

public class NoBookOrderItemsException extends RuntimeException {

    private static final String MESSAGE = "The order placed on %s has no order line items. The order was not placed.";

    public NoBookOrderItemsException(LocalDateTime orderTime) {
        super(MESSAGE.formatted(orderTime));
    }
}
