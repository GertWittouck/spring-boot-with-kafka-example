package com.gwi.kafka.producer.book.order.service;

public interface MessageProducer<T> {

    void sendMessage(String topic, T message);
}
