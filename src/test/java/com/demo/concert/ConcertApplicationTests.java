package com.demo.concert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.demo.concert.config.RedisLuaScript;
import com.demo.concert.dto.ReservationEvent;
import com.demo.concert.service.ReservationService;
import com.demo.concert.service.ReservationServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class ConcertApplicationTests {
  @Autowired
  private ReservationServiceImpl reservationService;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private RedisLuaScript redisLuaScript;

  private KafkaTemplate<String, Object> kafkaTemplate;


  @BeforeEach
  void setup() {
    redisTemplate.opsForValue().set("concert:1:stock", "5");
    redisTemplate.delete("concert:1:users");

    kafkaTemplate = mock(KafkaTemplate.class);
    reservationService = new ReservationServiceImpl(redisLuaScript, kafkaTemplate);
  }

  @Test
  void concurrent_reservation_test() throws InterruptedException {
    int threadCount = 10; // 동시에 10명이 시도
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    List<Boolean> results = Collections.synchronizedList(new ArrayList<>());

    for (int i = 0; i < threadCount; i++) {
      final String userId = "user" + i;
      executor.submit(() -> {
        try {
          boolean reserved = reservationService.reserve("1", userId);
          results.add(reserved);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();

    // 성공한 유저는 5명이어야 함
    long successCount = results.stream().filter(r -> r).count();
    assertThat(successCount).isEqualTo(5);

    // Kafka 발행 검증
    ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);
    verify(kafkaTemplate, times(5)).send(eq("reservation-events"), eventCaptor.capture());

    List<Object> sentEvents = eventCaptor.getAllValues();
    assertThat(sentEvents).hasSize(5);

  }
}
