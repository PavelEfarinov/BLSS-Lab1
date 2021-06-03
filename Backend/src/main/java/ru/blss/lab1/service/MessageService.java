package ru.blss.lab1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.order.Order;

@Service
@Slf4j
public class MessageService {
    private final KafkaTemplate<Long, Order> kafkaStarshipTemplate;


    public MessageService(KafkaTemplate<Long, Order> kafkaStarshipTemplate) {
        this.kafkaStarshipTemplate = kafkaStarshipTemplate;
    }

    public void send(Order order) {
        kafkaStarshipTemplate.send("orders", order);
    }

    @KafkaListener(id = "order-consumer", topics = {"orders"}, containerFactory = "singleFactory")
    public void consume(Order dto) {
        log.info("=> consumed {}", dto.toString());
    }
}
