package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import com.example.cdc_payload.domain.interaction.repository.InteractionRepository;
import com.example.cdc_payload.global.infra.kafka.out.NewPayloadData;
import com.example.cdc_payload.global.infra.kafka.out.PayloadLogProducer;
import java.util.List;
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
    public void newCaptureEvent(List<Map<String, Object>> event){
        System.out.println("이벤트 수신...");

//       {ROW_ID=AAATNPAAHAAAALkAEy,
//        OPERATION=INSERT,
//        SEG_OWNER=C##DEEP,
//        TABLE_NAME=INTERACTION,
//        SQL_REDO=insert into "C##DEEP"."INTERACTION"("IDX","COMMENT_IDX","EMOJI_IDX","USER_IDX") values ('19835','5','484','3');}

        for (Map<String, Object> stringObjectMap : event) {
            try {
                System.out.println(event.get("ROW_ID"));
                interaction = interactionRepository.findByRowId( event.get("ROW_ID").toString());

//                System.out.println(interaction.getUser().getName());

                payloadLogProducer.sendNewPayloadLogCaptureMessage(NewPayloadData.builder()
                        .operation(stringObjectMap.get("OPERATION").toString())
                        .tableName(stringObjectMap.get("TABLE_NAME").toString())
                        .interaction(interaction).build());

            } catch (Exception e) {
                // 예외 처리 및 로그 출력
                System.err.println("이벤트 처리 중 오류 발생: " + e.getMessage());
                System.out.println(event.get("ROW_ID"));
                System.out.println(interaction.getIdx());
                e.printStackTrace();
            }
        }
    }
}
