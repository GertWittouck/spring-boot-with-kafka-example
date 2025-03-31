package com.gwi.kafka.consumer.invoice.book.order;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public record BookOrder(Collection<BookOrderItem> bookOrderItems) {

    public Collection<BookOrderItem> bookOrderItems() {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            return List.of();
        }
        return List.copyOf(bookOrderItems);
    }
}
