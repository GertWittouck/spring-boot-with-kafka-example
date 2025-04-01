package com.gwi.kafka.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.List;

public record BookOrderMessage(@JsonProperty("bookOrderItems") List<BookOrderItemMessage> bookOrderItems) {

    public List<BookOrderItemMessage> bookOrderItems() {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            return List.of();
        }
        return List.copyOf(bookOrderItems);
    }
}
