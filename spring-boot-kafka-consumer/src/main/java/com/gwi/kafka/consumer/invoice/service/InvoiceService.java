package com.gwi.kafka.consumer.invoice.service;


public interface InvoiceService<T,V> {
    V invoice(T order);
}
