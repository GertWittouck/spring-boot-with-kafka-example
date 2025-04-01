package com.gwi.kafka.messages;

public record BookOrderItemMessage(
        String isbn,
        int quantity
) {

    @Override
    public String toString() {
        return "BookOrderItemDto{" +
                "isbn='" + isbn + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
