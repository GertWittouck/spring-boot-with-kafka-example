package com.gwi.kafka.consumer.book.service;

import com.gwi.kafka.consumer.book.entities.Book;
import com.gwi.kafka.consumer.book.exceptions.DuplicateBookException;
import com.gwi.kafka.consumer.book.repository.BookRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Book> getAll() {
        logger.info("Finding all available books");
        return repository.findAll();
    }

    @Override
    public Optional<Book> getByIsbn(@NonNull String isbn) {
        Objects.requireNonNull(isbn, "A ISBN number is required to retrieve a book");
        logger.info("Finding a book matching ISBN {}", isbn);
        return repository.findByIsbn(isbn);
    }

    @Override
    public Book create(@NonNull Book book) {
        if (repository.findByIsbn(book.getIsbn()).isPresent()) {
            logger.warn("An existing book with ISBN {} was found. No new book will be created.", book.getIsbn());
            throw new DuplicateBookException(book.getIsbn());
        }
        logger.info("Creating a new book for ISBN {}", book.getIsbn());
        return repository.save(book);
    }
}
