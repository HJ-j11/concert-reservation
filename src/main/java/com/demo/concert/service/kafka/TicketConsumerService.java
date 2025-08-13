package com.demo.concert.service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "reservation-topic", groupId = "ticket-group")
@RequiredArgsConstructor
public class TicketConsumerService {

}
