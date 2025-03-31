package com.gwi.kafka.consumer.invoice.service;

import com.gwi.kafka.consumer.invoice.book.order.BookOrder;
import com.gwi.kafka.consumer.invoice.calculator.PriceCalculator;
import com.gwi.kafka.consumer.invoice.entities.Invoice;
import com.gwi.kafka.consumer.invoice.entities.InvoiceLineItem;
import com.gwi.kafka.consumer.invoice.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Service
public class BookOrderInvoiceService implements InvoiceService<BookOrder, Invoice> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderInvoiceService.class);
    private static final BigDecimal TAX_PERCENTAGE = BigDecimal.valueOf(20).setScale(2, HALF_UP);

    private final InvoiceRepository invoiceRepository;

    public BookOrderInvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice invoice(BookOrder order) {
        if (CollectionUtils.isEmpty(order.bookOrderItems())) {
            return createBlankInvoice();
        }
        return createInvoice(order);
    }

    private Invoice createBlankInvoice() {
        LOGGER.info("Saving a blank invoice as no book order items found.");
        return invoiceRepository.save(Invoice.builder()
                .invoiceLineItems(List.of())
                .tax(TAX_PERCENTAGE)
                .priceBeforeTax(BigDecimal.ZERO.setScale(2, HALF_UP))
                .priceAfterTax(BigDecimal.ZERO.setScale(2, HALF_UP))
                .build());
    }

    private Invoice createInvoice(BookOrder order) {
        var invoice = Invoice.builder()
                .tax(TAX_PERCENTAGE)
                .build();

        var invoiceLineItems = createInvoiceLineItems(order, invoice);

        var totalPriceBeforeTax = PriceCalculator.priceBeforeTax(invoiceLineItems);
        var totalPriceAfterTax = PriceCalculator.priceAfterTax(totalPriceBeforeTax, TAX_PERCENTAGE);
        LOGGER.info("Saving invoice for book order {}", order);
        return invoiceRepository.save(invoice
                .withItems(invoiceLineItems)
                .withPriceBeforeTax(totalPriceBeforeTax)
                .withPriceAfterTax(totalPriceAfterTax));
    }

    private static List<InvoiceLineItem> createInvoiceLineItems(BookOrder order, Invoice invoice) {
        LOGGER.info("Creating {} invoice line items", order.bookOrderItems().size());
        return order.bookOrderItems().stream()
                .map(bookOrderItem -> InvoiceLineItem.builder()
                        .invoice(invoice)
                        .book(bookOrderItem.book())
                        .build())
                .toList();
    }
}
