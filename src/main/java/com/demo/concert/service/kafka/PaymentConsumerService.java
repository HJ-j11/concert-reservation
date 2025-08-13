package com.demo.concert.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "order-progress", groupId = "ticket-group")
@RequiredArgsConstructor
public class PaymentConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void consumeOrder(String reservationId) {
        // 발행한 topic -> 연쇄 topic 발행
    }
}
