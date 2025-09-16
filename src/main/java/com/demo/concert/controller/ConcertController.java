package com.demo.concert.controller;

import com.demo.concert.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concert")
public class ConcertController {
    private final ReservationService reservationService;

    @PostMapping("reserve/{concertId}")
    public ResponseEntity<String> reserve(
        @PathVariable String concertId,
        @RequestParam String userId) {

        boolean success = reservationService.reserve(concertId, userId);

        if (success) return ResponseEntity.ok("Reservation successful!");
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation failed!");
    }
}
