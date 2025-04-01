package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.producer.book.order.entities.BookOrder;
import com.gwi.kafka.messages.BookOrderMessage;

public interface BookOrderService {
    BookOrder placeOrder(BookOrderMessage bookOrder);
}
