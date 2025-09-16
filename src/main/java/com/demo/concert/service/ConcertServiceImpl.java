package com.demo.concert.service;

import com.demo.concert.config.RedisLuaScript;
import com.demo.concert.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {
    private final RedisLuaScript luaScripts;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public boolean reserve(String concertId, String userId) {
        Long result = luaScripts.tryReserve(concertId, userId);

        if (result == 1L) {
            kafkaTemplate.send("reservation-events", new ReservationDto(concertId, userId));
            return true;
        } else if (result == -1L) {
            return false;
        } else {
            return false;
        }
    }
}
