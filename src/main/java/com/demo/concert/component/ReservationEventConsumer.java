package com.demo.concert.component;

import com.demo.concert.dto.ReservationEvent;
import com.demo.concert.entity.Reservation;
import com.demo.concert.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationEventConsumer {
    private final ReservationRepository reservationRepository;

    @KafkaListener(topics = "reservation-events", groupId = "reservation-group")
    public void consume(ReservationEvent event) {
        log.info("Reservation confirmed: {}", event.toString());
        Reservation reservation = new Reservation();
        reservation.setConcertId(event.getConcertId());
        reservation.setUserId(event.getUserId());
        reservationRepository.save(reservation);
    }
}