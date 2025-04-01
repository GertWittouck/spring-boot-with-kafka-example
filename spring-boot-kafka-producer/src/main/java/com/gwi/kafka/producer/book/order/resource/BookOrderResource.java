package com.gwi.kafka.producer.book.order.resource;

import com.gwi.kafka.messages.BookOrderMessage;
import com.gwi.kafka.messages.BookOrderItemMessage;
import com.gwi.kafka.producer.book.order.resource.model.BookOrderItemRequest;
import com.gwi.kafka.producer.book.order.resource.model.BookOrderRequest;
import com.gwi.kafka.producer.book.order.resource.model.BookOrderResponse;
import com.gwi.kafka.producer.book.order.service.BookOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/api/v1/book-order")
public class BookOrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderResource.class);

    private final BookOrderService bookOrderService;

    public BookOrderResource(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @PostMapping
    public ResponseEntity<BookOrderResponse> placeOrder(@RequestBody BookOrderRequest bookOrderRequest) {
        LOGGER.info("Request received to place order {}", bookOrderRequest);
        var orderToPlace = convertBookOrderRequest().apply(bookOrderRequest);
        var bookOrder = bookOrderService.placeOrder(orderToPlace);
        LOGGER.info("Book Order {} has been placed.", bookOrder);
        return ResponseEntity.ok().body(BookOrderResponse.of(bookOrder));
    }

    private Function<BookOrderRequest, BookOrderMessage> convertBookOrderRequest() {
        return request -> new BookOrderMessage(
                request.bookOrderItems().stream()
                        .map(item -> convertBookOrderItem().apply(item))
                        .toList()
        );
    }

    private Function<BookOrderItemRequest, BookOrderItemMessage> convertBookOrderItem() {
        return bookOrderItemRequest -> new BookOrderItemMessage(
                bookOrderItemRequest.isbn(),
                bookOrderItemRequest.quantity()
        );
    }
}
