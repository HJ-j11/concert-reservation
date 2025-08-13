package com.demo.concert.service.kafka;

import com.demo.concert.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        log.info("topic : {}, message : {} ", topic, message);
        kafkaTemplate.send(topic, message);
    }

    public void produceReservation(Order order) {
        // Order 객체 만들기

        // PaymentConsumer 로 보내기
        kafkaTemplate.send("order-progress", order.getOrderId());
    }
}
