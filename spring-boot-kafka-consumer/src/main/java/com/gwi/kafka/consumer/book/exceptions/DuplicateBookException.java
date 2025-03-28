package com.gwi.kafka.consumer.book.exceptions;

import lombok.NonNull;

public class DuplicateBookException extends RuntimeException {

    private static final String MESSAGE = "An existing book is found for ISBN %s";

    public DuplicateBookException(@NonNull String isbn) {
        super(MESSAGE.formatted(isbn));
    }
}
