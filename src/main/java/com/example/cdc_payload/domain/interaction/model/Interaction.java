package com.example.cdc_payload.domain.interaction.model;

import com.example.cdc_payload.domain.comment.Comments;
import com.example.cdc_payload.domain.emoji.Emoji;
import com.example.cdc_payload.domain.user.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interaction {
    @Id
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "comment_idx")
    private Comments COMMENTS;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private Users USERS;

    @ManyToOne
    @JoinColumn(name = "emoji_idx")
    private Emoji emoji;
}