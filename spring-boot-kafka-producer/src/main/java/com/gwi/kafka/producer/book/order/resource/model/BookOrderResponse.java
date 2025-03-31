package com.gwi.kafka.producer.book.order.resource.model;

import com.gwi.kafka.producer.book.order.entities.BookOrder;

import java.util.List;

public record BookOrderResponse(List<BookOrderItemResponse> bookOrderItems) {

    public static BookOrderResponse of(BookOrder bookOrder) {
        return new BookOrderResponse(
                bookOrder.getBookOrderItems().stream()
                        .map(BookOrderItemResponse::of)
                        .toList()
        );
    }
}
