package com.gwi.kafka.consumer.book.repository;

import com.gwi.kafka.consumer.book.entities.Book;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(@NonNull String isbn);
}
