package com.demo.concert.service;

import com.demo.concert.config.RedisLuaScript;
import com.demo.concert.dto.ReservationEvent;
import com.demo.concert.dto.api.ApiResponse;
import com.demo.concert.dto.api.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final RedisLuaScript luaScripts;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public boolean reserve(String concertId, String userId) {
    Long result = luaScripts.tryReserve(concertId, userId);

    if (result == 1L) {
      kafkaTemplate.send("reservation-events", new ReservationEvent(concertId, userId));
      return true;
    } else if (result == -1L) {
      // already reserved
      return false;
    } else {
      // stock empty
      return false;
    }
  }

  @Override
  public void getTicketStock(String concertId) {

  }
}
