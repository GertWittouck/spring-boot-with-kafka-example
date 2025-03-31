package com.gwi.kafka.consumer.invoice.service;

public interface MessageConsumer<T,V> {
    V consume(T message);
}
