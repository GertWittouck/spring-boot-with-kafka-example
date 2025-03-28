package com.gwi.kafka.consumer.book.service;

import com.gwi.kafka.consumer.book.entities.Book;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface BookService {
    Collection<Book> getAll();
    Optional<Book> getByIsbn(@NonNull String isbn);

    Book create(@NonNull Book book);

}
