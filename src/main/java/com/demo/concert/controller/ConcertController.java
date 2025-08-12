package com.demo.concert.controller;

import com.demo.concert.service.ConcertService;
import com.demo.concert.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ConcertController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestParam String message) {
        kafkaProducerService.send("my-topic", message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
