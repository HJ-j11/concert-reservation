package com.demo.concert.controller;

import com.demo.concert.service.ConcertServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kafka")
public class ConcertController {
    private final ConcertServiceImpl concertService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestParam String message) {
        return null;
    }
}
