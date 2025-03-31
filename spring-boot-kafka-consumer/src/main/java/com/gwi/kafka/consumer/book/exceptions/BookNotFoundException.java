package com.gwi.kafka.consumer.book.exceptions;

public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE = "No book found for ISBN %s";

    public BookNotFoundException(String isbn) {
        super(MESSAGE.formatted(isbn));
    }
}
