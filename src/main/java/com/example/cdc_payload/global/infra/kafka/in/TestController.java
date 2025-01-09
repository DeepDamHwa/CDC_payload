package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.interaction.model.Interaction;
import com.example.cdc_payload.domain.interaction.repository.InteractionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interaction")
public class TestController {
    private final ChangeLogConsumer changeLogConsumer;
    private final InteractionRepository interactionRepository;
    @PostMapping("/capture")
    public ResponseEntity<String> capture( ){ //@RequestBody NewInteractionCaptureEvent req

        Map<String, Object> event = new HashMap<>();
        event.put("ROW_ID", "AAATNPAAHAAAALkAEy");
        event.put("OPERATION", "INSERT");
        event.put("SEG_OWNER", "C##DEEP");
        event.put("TABLE_NAME", "INTERACTION");
        event.put("SQL_REDO", "insert into \"C##DEEP\".\"INTERACTION\"(\"IDX\",\"COMMENT_IDX\",\"EMOJI_IDX\",\"USER_IDX\") values ('19835','5','484','3');");


        changeLogConsumer.newCaptureEvent(event);
        return ResponseEntity.ok("good");
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        Interaction interaction = interactionRepository.findByRowId("AAATNPAAHAAAALkAFM");
//        return ResponseEntity.ok(interaction.getUser().getName());
        return ResponseEntity.ok("ff");
    }

}
