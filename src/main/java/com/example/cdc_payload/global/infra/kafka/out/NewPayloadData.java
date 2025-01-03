package com.example.cdc_payload.global.infra.kafka.out;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NewPayloadData {
    private String operation;
    private String tableName;
    private Interaction interaction;
}
