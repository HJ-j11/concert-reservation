package com.demo.concert.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        log.info("topic : {}, message : {} ", topic, message);
        kafkaTemplate.send(topic, message);
    }
}
