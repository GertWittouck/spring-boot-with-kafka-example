package com.gwi.kafka.producer.book.order.model;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public record Books(Collection<Book> books) {

    public Collection<Book> books() {
        if (CollectionUtils.isEmpty(books)) {
            return List.of();
        }
        return List.copyOf(books);
    }
}
