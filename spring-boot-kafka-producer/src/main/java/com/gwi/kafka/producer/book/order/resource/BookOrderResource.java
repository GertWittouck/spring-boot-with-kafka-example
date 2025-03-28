package com.gwi.kafka.producer.book.order.resource;

import com.gwi.kafka.producer.book.order.resource.model.BookOrderRequest;
import com.gwi.kafka.producer.book.order.resource.model.BookOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book-order")
public class BookOrderResource {

    @PostMapping
    public ResponseEntity<BookOrderResponse> placeOrder(@RequestBody BookOrderRequest bookOrder) {
        // TODO: place a new order
        return ResponseEntity.ok().body(new BookOrderResponse());
    }
}
