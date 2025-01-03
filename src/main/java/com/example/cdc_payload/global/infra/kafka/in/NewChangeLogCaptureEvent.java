package com.example.cdc_payload.global.infra.kafka.in;

import java.util.Map;
import lombok.Getter;

@Getter
public class NewChangeLogCaptureEvent {
    private Map<String, Object> logs;
}
