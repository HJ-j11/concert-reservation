package com.demo.concert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    private String id;
    private LocalDateTime createdAt;
}
