package com.gwi.kafka.consumer.invoice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

@Entity
@Table(schema = "book-shop", name = "invoice")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Invoice implements Serializable {
    @Serial
    private static final long serialVersionUID = -8463698465050044098L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_sequence")
    @SequenceGenerator(name = "invoice_sequence", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceLineItem> invoiceLineItems;

    @Column(name = "price_excl_vat", nullable = false)
    private BigDecimal priceBeforeTax = ZERO.setScale(2, HALF_UP);
    @Column(name = "tax", nullable = false)
    private BigDecimal tax = ZERO.setScale(2, HALF_UP);
    @Column(name = "price", nullable = false)
    private BigDecimal priceAfterTax = ZERO.setScale(2, HALF_UP);
}
