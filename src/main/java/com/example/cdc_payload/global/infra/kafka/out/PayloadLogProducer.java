package com.example.cdc_payload.global.infra.kafka.out;

import com.example.cdc_payload.domain.comment.model.NewCommentsPayloadData;
import com.example.cdc_payload.domain.emoji.model.NewEmojiPayloadData;
import com.example.cdc_payload.domain.interaction.model.NewInteractionPayloadData;
import com.example.cdc_payload.domain.post.model.NewPostPayloadData;
import com.example.cdc_payload.domain.role.model.NewRolePayloadData;
import com.example.cdc_payload.domain.user.model.NewUsersPayloadData;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayloadLogProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public void sendNewPayloadLogCaptureMessage(NewPayloadData newPayloadData, String topic){
        kafkaTemplate.send(topic, newPayloadData);
        //"payload_log"
    }
}