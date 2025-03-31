package com.gwi.kafka.producer.book.order.exceptions;

import java.time.LocalDateTime;

public class MissingBookOrderDataException extends RuntimeException {

    private static final String MESSAGE = "No order data was found to place an order on %s";

    public MissingBookOrderDataException(LocalDateTime orderTime) {
        super(MESSAGE.formatted(orderTime));
    }
}
