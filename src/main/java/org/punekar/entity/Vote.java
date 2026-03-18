package org.punekar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_id", "target_type"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @JsonBackReference
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonBackReference
    private Comment comment;

    private Long targetId; // Issue.id or Comment.id

    @Column(nullable = false)
    private String targetType; // ISSUE, COMMENT

    @Enumerated(EnumType.STRING)
    private VoteType voteType; // +1 (upvote), -1 (downvote)

    public enum VoteType {
        UPVOTE,
        DOWNVOTE
    }
}