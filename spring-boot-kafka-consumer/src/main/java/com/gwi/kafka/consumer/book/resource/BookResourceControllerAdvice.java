package com.gwi.kafka.consumer.book.resource;

import com.gwi.kafka.consumer.book.exceptions.DuplicateBookException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookResourceControllerAdvice {

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<String> handleDuplicateBookException(DuplicateBookException exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
