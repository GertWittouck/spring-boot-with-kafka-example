package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.producer.book.order.entities.BookOrder;
import com.gwi.kafka.producer.book.order.model.BookOrderDto;

public interface BookOrderService {
    BookOrder placeOrder(BookOrderDto bookOrder);
}
