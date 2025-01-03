package com.example.cdc_payload.global.infra.kafka.out;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeLogProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void sendNewChangeLogCaptureMessage(Object newChangeLogData){
        kafkaTemplate.send("change_log", newChangeLogData);
    }
}