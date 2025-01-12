package com.example.cdc_payload.global.infra.kafka.in;

import com.example.cdc_payload.domain.EventEntity;
import com.example.cdc_payload.domain.comment.model.Comments;
import com.example.cdc_payload.domain.comment.model.NewCommentsPayloadData;
import com.example.cdc_payload.domain.comment.repository.CommentsRepository;
import com.example.cdc_payload.domain.emoji.model.Emoji;
import com.example.cdc_payload.domain.emoji.repository.EmojiRepository;
import com.example.cdc_payload.domain.interaction.model.Interaction;
import com.example.cdc_payload.domain.interaction.repository.InteractionRepository;
import com.example.cdc_payload.domain.post.model.Post;
import com.example.cdc_payload.domain.post.repository.PostRepository;
import com.example.cdc_payload.domain.role.model.Role;
import com.example.cdc_payload.domain.role.repository.RoleRepository;
import com.example.cdc_payload.domain.user.model.Users;
import com.example.cdc_payload.domain.user.repository.UserRepository;
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
    private final CommentsRepository commentsRepository;
    private final EmojiRepository emojiRepository;
    private final InteractionRepository interactionRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PayloadLogProducer payloadLogProducer;

    @KafkaListener(topics = "change_log", groupId = "change_log_group")
    public void newCaptureEvent(Map<String, Object> event){
        System.out.println("이벤트 수신...");

//       {ROW_ID=AAATNPAAHAAAALkAEy,
//        OPERATION=INSERT,
//        SEG_OWNER=C##DEEP,
//        TABLE_NAME=INTERACTION,
//        SQL_REDO=insert into "C##DEEP"."INTERACTION"("IDX","COMMENT_IDX","EMOJI_IDX","USER_IDX") values ('19835','5','484','3');}

        String tableName = event.get("TABLE_NAME").toString();

        if(tableName.equals("COMMENTS")){
            Comments comments = commentsRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, comments, "comment_payload_log");
        }else if(tableName.equals("EMOJI")){
            Emoji emoji = emojiRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, emoji, "emoji_payload_log");
        }else if(tableName.equals("INTERACTION")){
            Interaction interaction = interactionRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, interaction, "interaction_payload_log");
        }else if(tableName.equals("POST")){
            Post post = postRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, post, "post_payload_log");
        }else if(tableName.equals("ROLE")){
            Role role = roleRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, role, "role_payload_log");
        }else if(tableName.equals("USER")){
            Users user = userRepository.findByRowId( event.get("ROW_ID").toString());
            produceEvent(event, user,"user_payload_log");
        }

    }

    private void produceEvent(Map<String, Object> event, EventEntity entity, String topic){
        try {
            payloadLogProducer.sendNewPayloadLogCaptureMessage(entity.toDto(event.get("OPERATION").toString()), topic);

        } catch (Exception e) {
            // 예외 처리 및 로그 출력
            System.err.println("이벤트 처리 중 오류 발생: " + e.getMessage());
            System.out.println(event.get("ROW_ID"));
            e.printStackTrace();
        }
    }

//    private void produceInteractionEvent(Map<String, Object> event){
//        Interaction interaction = null;
//        try {
//            System.out.println(event.get("ROW_ID"));
//            interaction = interactionRepository.findByRowId( event.get("ROW_ID").toString());
//
//            payloadLogProducer.sendNewPayloadLogCaptureMessage(NewPayloadData.builder()
//                    .operation(event.get("OPERATION").toString())
//                    .tableName(event.get("TABLE_NAME").toString())
//                    .interactionIdx(interaction.getIdx())
//                    .userIdx(interaction.getUSERS().getIdx())
//                    .commentIdx(interaction.getCOMMENTS().getIdx())
//                    .emojiIdx(interaction.getEmoji().getIdx())
//                    .build());
//
//        } catch (Exception e) {
//            System.err.println("이벤트 처리 중 오류 발생: " + e.getMessage());
//            System.out.println(event.get("ROW_ID"));
//            System.out.println(interaction.getIdx());
//            e.printStackTrace();
//        }
//    }
}
