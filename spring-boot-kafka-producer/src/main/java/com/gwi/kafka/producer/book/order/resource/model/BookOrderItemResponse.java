package com.gwi.kafka.producer.book.order.resource.model;

import com.gwi.kafka.producer.book.order.entities.BookOrderItem;
import jakarta.validation.constraints.NotNull;

public record BookOrderItemResponse(
        @NotNull(message = "An ISBN is required to order a book") String isbn,
        String bookTitle,
        String category,
        String note,
        int quantity) {

    public static BookOrderItemResponse of(BookOrderItem bookOrderItem) {
        return new BookOrderItemResponse(
                bookOrderItem.getIsbn(),
                bookOrderItem.getBookTitle(),
                bookOrderItem.getCategory().name(),
                bookOrderItem.getNote(),
                bookOrderItem.getQuantity()
        );
    }
}
