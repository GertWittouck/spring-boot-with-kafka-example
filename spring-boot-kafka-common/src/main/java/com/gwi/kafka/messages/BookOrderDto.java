package com.gwi.kafka.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.List;

public record BookOrderDto(@JsonProperty("bookOrderItems") List<BookOrderItemDto> bookOrderItems) {

    public List<BookOrderItemDto> bookOrderItems() {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            return List.of();
        }
        return List.copyOf(bookOrderItems);
    }
}
