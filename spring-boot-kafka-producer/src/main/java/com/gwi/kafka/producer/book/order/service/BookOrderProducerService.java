package com.gwi.kafka.producer.book.order.service;

import com.gwi.kafka.messages.BookOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookOrderProducerService implements MessageProducer<BookOrderDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookOrderProducerService.class);

    private final KafkaTemplate<String, BookOrderDto> kafkaTemplate;

    public BookOrderProducerService(KafkaTemplate<String, BookOrderDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topic, BookOrderDto bookOrder) {
        LOGGER.info("Placing message {} on topic {}", bookOrder, topic);
        kafkaTemplate.send(topic, bookOrder);
    }
}
