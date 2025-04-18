package com.gwi.kafka.producer.book.order.resource.model;

import org.springframework.util.CollectionUtils;

import java.util.List;

public record BookOrderRequest(List<BookOrderItemRequest> bookOrderItems) {

    public List<BookOrderItemRequest> bookOrderItems() {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            return List.of();
        }
        return List.copyOf(bookOrderItems);
    }
}
