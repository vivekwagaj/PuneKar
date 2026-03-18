package org.punekar.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CommentDTO {
    private Long id;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
}