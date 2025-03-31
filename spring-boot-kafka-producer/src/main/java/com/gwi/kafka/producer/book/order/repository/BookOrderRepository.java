package com.gwi.kafka.producer.book.order.repository;

import com.gwi.kafka.producer.book.order.entities.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
}
