package com.demo.concert.component;

import com.demo.concert.dto.ReservationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationConsumer {

    @KafkaListener(topics = "reservation-events", groupId = "reservation-service")
    public void consume(ReservationEvent event) {
        log.info("Reservation confirmed: {}", event);
    }
}