package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import com.example.cdc_payload.domain.interaction.repository.InteractionRepository;
import com.example.cdc_payload.global.infra.kafka.out.PayloadLogProducer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeLogConsumer {
    private final InteractionRepository interactionRepository;
    private final PayloadLogProducer payloadLogProducer;

    @KafkaListener(topics = "change_log", groupId = "change_log_group")
    public void newCaptureEvent(Map<String, Object> event){
        System.out.println("이벤트 수신...");

        //{ROW_ID=AAATNPAAHAAAALkAEy,
//        OPERATION=INSERT,
//        SEG_OWNER=C##DEEP,
//        TABLE_NAME=INTERACTION,
//        SQL_REDO=insert into "C##DEEP"."INTERACTION"("IDX","COMMENT_IDX","EMOJI_IDX","USER_IDX") values ('19835','5','484','3');}


        //rowID로 조회해오기
        String rowId = event.get("ROW_ID").toString();
        Interaction interaction = interactionRepository.findByRowId(rowId);

        //발행하기
        payloadLogProducer.sendNewPayloadLogCaptureMessage(NewPayloadData.builder()
                .operation(event.get("OPERATION").toString())
                .tableName(event.get("TABLE_NAME").toString())
                .interaction(interaction).build());
    }
}
