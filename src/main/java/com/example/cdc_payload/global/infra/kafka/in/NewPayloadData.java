package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewPayloadData {
    private String operation;
    private String tableName;
    private Interaction interaction;

//    OPERATION=INSERT,
//        SEG_OWNER=C##DEEP,
//        TABLE_NAME=INTERACTION,
//        SQL_REDO=insert into "C##DEEP"."INTERACTION"("IDX","COMMENT_IDX","EMOJI_IDX","USER_IDX") values ('19835','5','484','3');}
}
