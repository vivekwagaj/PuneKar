package org.punekar.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class VerificationDTO {
    private Long id;
    private Long issueId;
    private Long userId;
    private String imageUrl;
    private String note;
    private LocalDateTime createdAt;
}