package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.messages.BookOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookOrderProducerService implements MessageProducer<BookOrderMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderProducerService.class);

    private final KafkaTemplate<String, BookOrderMessage> kafkaTemplate;

    public BookOrderProducerService(KafkaTemplate<String, BookOrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, BookOrderMessage bookOrder) {
        LOGGER.info("Placing message {} on topic {}", bookOrder, topic);
        kafkaTemplate.send(topic, bookOrder);
    }
}
