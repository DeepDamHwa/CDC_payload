package com.example.cdc_payload.global.infra.kafka.out;

import com.example.cdc_payload.global.infra.kafka.in.NewPayloadData;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayloadLogProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void sendNewPayloadLogCaptureMessage(NewPayloadData newPayloadData){
        kafkaTemplate.send("payload_log", newPayloadData);
    }
}