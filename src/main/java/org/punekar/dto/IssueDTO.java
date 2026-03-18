package org.punekar.dto;

import lombok.*;
import org.punekar.entity.IssueImage;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
    private String createdById;
    private String wardId;
    private List<String> imageUrls;  // changed to List<String>
}