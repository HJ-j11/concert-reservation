package com.demo.concert.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "reservation-topic", groupId = "ticket-group")
public class PaymentConsumerService {

}
