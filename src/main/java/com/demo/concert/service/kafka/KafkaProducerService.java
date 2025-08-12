package com.demo.concert.service.kafka;

public interface KafkaProducerService {
    public void sendMessage(String topic, String message);
}
