package com.gwi.kafka.producer.book.order.entities;

import com.gwi.kafka.producer.constants.BookCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(schema = "book-shop", name = "book-order-item")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookOrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 6963519600502633740L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_order_item_sequence")
    @SequenceGenerator(name = "book_order_item_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "title")
    private String bookTitle;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    @Column(name = "description")
    private String note;
    @ManyToOne
    @JoinColumn(name = "book_order_id")
    private BookOrder bookOrder;

}
