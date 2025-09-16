package com.demo.concert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reservationId;
    private String userId;
    private String concertId;

    public Reservation(String userId, String concertId) {
        this.userId = userId;
        this.concertId = concertId;
    }
}
