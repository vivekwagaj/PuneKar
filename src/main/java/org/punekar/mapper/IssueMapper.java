package org.punekar.mapper;

import org.punekar.dto.IssueDTO;
import org.punekar.entity.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class IssueMapper {

    public static IssueDTO toDTO(Issue issue) {
        if (issue == null) return null;

        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setCategory(issue.getCategory());
        dto.setStatus(String.valueOf(issue.getStatus()));
        dto.setCreatedAt(issue.getCreatedAt());
        if (issue.getStatus() == Issue.IssueStatus.RESOLVED && issue.getVerifications() != null) {
            dto.setResolvedAt(
                    issue.getVerifications().stream()
                            .map(Verification::getCreatedAt)
                            .max(LocalDateTime::compareTo)
                            .orElse(issue.getCreatedAt())
            );
        }



        // Reference IDs instead of full objects
        if (issue.getUser() != null) {
            dto.setCreatedById(String.valueOf(issue.getUser().getId()));
        }
        if (issue.getWard() != null) {
            dto.setWardId(String.valueOf(issue.getWard().getId()));
        }

        // Images (if present)
        if (issue.getImages() != null) {
            dto.setImageUrls(
                    issue.getImages().stream()
                            .map(IssueImage::getImageUrl)
                            .collect(Collectors.toList())
            );
        }


        return dto;
    }

    public static Issue toEntity(IssueDTO dto, User creator, Ward ward) {
        if (dto == null) return null;

        Issue issue = new Issue();
        issue.setId(dto.getId()); // optional, usually null for create
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCategory(dto.getCategory());
        if (dto.getStatus() != null) {
            issue.setStatus(Issue.IssueStatus.valueOf(dto.getStatus()));
        } else {
            issue.setStatus(Issue.IssueStatus.OPEN);
        }
        issue.setCreatedAt(dto.getCreatedAt());
        issue.setUpdatedAt(dto.getResolvedAt());

        issue.setUser(creator);
        issue.setWard(ward);

        return issue;
    }
}
