package com.demo.concert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Concert {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String name;
}
