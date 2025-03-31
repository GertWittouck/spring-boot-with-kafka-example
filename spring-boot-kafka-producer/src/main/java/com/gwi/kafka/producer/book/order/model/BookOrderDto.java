package com.gwi.kafka.producer.book.order.model;

import org.springframework.util.CollectionUtils;

import java.util.List;

public record BookOrderDto(List<BookOrderItemDto> bookOrderItems) {

    public List<BookOrderItemDto> bookOrderItems() {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            return List.of();
        }
        return List.copyOf(bookOrderItems);
    }
}
