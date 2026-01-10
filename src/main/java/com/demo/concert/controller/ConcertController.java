package com.demo.concert.controller;

import com.demo.concert.dto.api.ApiResponse;
import com.demo.concert.entity.login.CustomUserDetails;
import com.demo.concert.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ConcertController {
    private final ReservationService reservationService;

    @GetMapping("/{concertId}/stock")
    public ResponseEntity<ApiResponse<Long>> getTicketStock(@PathVariable String concertId) {
        Long stock = reservationService.getTicketStock(concertId);
        ApiResponse<Long> apiResponse = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Ticket is still remained",
            stock
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{concertId}")
    public ResponseEntity<ApiResponse<Void>> reserve(
        @PathVariable String concertId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        String userId = userDetails.getUsername();
        boolean success = reservationService.reserve(concertId, userId);

        if (success) {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Reservation successful!",
                null
            );
            return ResponseEntity.ok(apiResponse);
        } else {
            ApiResponse<Void> apiResponse = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Reservation failed! Stock may be empty or you have already reserved.",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
