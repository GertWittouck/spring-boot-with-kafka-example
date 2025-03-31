package com.gwi.kafka.consumer.invoice.calculator;

import com.gwi.kafka.consumer.book.entities.Book;
import com.gwi.kafka.consumer.invoice.entities.InvoiceLineItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PriceCalculator {

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, HALF_UP);

    public static BigDecimal priceBeforeTax(List<InvoiceLineItem> invoiceLineItems) {
        if (CollectionUtils.isEmpty(invoiceLineItems)) {
            return ZERO;
        }
        return invoiceLineItems.stream()
                .map(InvoiceLineItem::getBook)
                .map(Book::getPriceBeforeTax)
                .reduce(ZERO, BigDecimal::add);
    }

    public static BigDecimal priceAfterTax(BigDecimal priceBeforeTax, BigDecimal taxPercentage) {
        var tax = BigDecimal.ONE.add(taxPercentage.divide(BigDecimal.valueOf(100), HALF_UP));
        return priceBeforeTax.multiply(tax);
    }
}
