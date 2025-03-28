package com.gwi.kafka.consumer.book.resource.model;

import com.gwi.kafka.consumer.book.entities.Book;
import lombok.NonNull;

public record BookItemResponse(
        String title,
        String author,
        String isbn,
        String description
) {

    public static BookItemResponse of(@NonNull Book book) {
        return new BookItemResponse(
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getDescription()
        );
    }
}
