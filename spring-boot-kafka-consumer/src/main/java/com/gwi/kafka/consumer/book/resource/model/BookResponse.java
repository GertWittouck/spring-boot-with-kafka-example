package com.gwi.kafka.consumer.book.resource.model;

import com.gwi.kafka.consumer.book.entities.Book;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public record BookResponse(Collection<BookItemResponse> books) {

    public static BookResponse of(Collection<Book> books) {
        if (CollectionUtils.isEmpty(books)) {
            return new BookResponse(List.of());
        }

        var bookResponse = books.stream()
                .map(BookItemResponse::of)
                .toList();

        return new BookResponse(bookResponse);
    }
}
