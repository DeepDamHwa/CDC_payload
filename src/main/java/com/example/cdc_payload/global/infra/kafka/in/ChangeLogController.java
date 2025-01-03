package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.global.infra.kafka.out.ChangeLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class ChangeLogController {
    private final ChangeLogProducer changeLogProducer;

    @PostMapping("/capture")
    public ResponseEntity<String> capture(
            @RequestBody NewChangeLogCaptureEvent req
    ){
        changeLogProducer.sendNewChangeLogCaptureMessage(req);

        return ResponseEntity.ok("good");
    }
}