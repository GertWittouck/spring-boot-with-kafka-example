package com.gwi.kafka.producer.book.order.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(schema = "book-shop", name = "book-order")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BookOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = -2817610658431219318L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_order_sequence")
    @SequenceGenerator(name = "book_order_sequence", allocationSize = 1)
    private Long id;
    @OneToMany(mappedBy = "bookOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookOrderItem> bookOrderItems;

    public List<BookOrderItem> getBookOrderItems() {
        return List.copyOf(bookOrderItems);
    }

    public BookOrder withBookOrderItems(List<BookOrderItem> bookOrderItems) {
        if (CollectionUtils.isEmpty(bookOrderItems)) {
            this.bookOrderItems = List.of();
            return this;
        }

        this.bookOrderItems = List.copyOf(bookOrderItems);
        return this;
    }
}
