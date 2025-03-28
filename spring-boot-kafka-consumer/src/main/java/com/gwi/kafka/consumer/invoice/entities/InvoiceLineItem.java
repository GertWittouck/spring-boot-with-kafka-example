package com.gwi.kafka.consumer.invoice.entities;

import com.gwi.kafka.consumer.book.entities.Book;
import jakarta.persistence.Entity;
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
import java.util.Optional;

@Entity
@Table(schema = "book-shop", name = "invoice-line-item")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InvoiceLineItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 6593590371560373607L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_line_item_seq")
    @SequenceGenerator(name = "invoice_line_item_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Override
    public String toString() {
        return """
                InvoiceLineItem: {
                    book title: %s,
                    author: %s,
                    isbn: %s,
                    description: %s,
                    price (Excl VAT): %s,
                }
                """.formatted(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    Optional.ofNullable(book.getDescription()).orElse("N/A"),
                    book.getPriceBeforeTax());
    }
}
