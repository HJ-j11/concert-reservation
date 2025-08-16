package com.demo.concert.service;

import com.demo.concert.dto.api.ApiResponse;
import com.demo.concert.dto.api.ReservationRequest;

public interface ReservationService {
  public ApiResponse requestReservation(ReservationRequest reservationRequest);
}
