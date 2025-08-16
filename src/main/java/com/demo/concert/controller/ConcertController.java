package com.demo.concert.controller;

import com.demo.concert.dto.api.ReservationRequest;
import com.demo.concert.service.ReservationServiceImpl;
import com.demo.concert.service.kafka.KafkaProducerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ConcertController {
    private final KafkaProducerServiceImpl kafkaProducerService;
    private final ReservationServiceImpl reservationService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestParam String message) {
        kafkaProducerService.sendMessage("my-topic", message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.requestReservation(request));
    }
}
