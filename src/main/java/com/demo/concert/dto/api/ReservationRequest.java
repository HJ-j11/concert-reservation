package com.demo.concert.dto.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {
  private String requestId;
  private String memberId;
  private String concertId;
  private String seatId;
}
