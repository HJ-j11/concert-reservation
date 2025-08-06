package com.demo.concert.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void consume(String message) {
        log.info("Received message: {}", message);
        // 메시지 처리 로직을 작성합니다.
    }
}
