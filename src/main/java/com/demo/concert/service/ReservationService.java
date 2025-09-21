package com.demo.concert.service;

public interface ReservationService {
  boolean reserve(String concertId, String userId);
  void getTicketStock(String concertId);
}
