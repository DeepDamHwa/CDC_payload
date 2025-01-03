package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import com.example.cdc_payload.domain.interaction.repository.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeLogConsumer {
    private final InteractionRepository interactionRepository;

    @KafkaListener(topics = "change_log", groupId = "payload_group")
    public void newCaptureEvent(NewChangeLogCaptureEvent event){
        System.out.println("이벤트 수신...");
        System.out.println(event.getLogs());

        //rowID로 조회해오기
        String rowId = "";
        Interaction interaction = interactionRepository.findByRowId(rowId);
    }
}
