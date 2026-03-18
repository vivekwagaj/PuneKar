package org.punekar.mapper;

import org.punekar.dto.VerificationDTO;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.entity.Verification;

import java.time.LocalDateTime;

public class VerificationMapper {

    public static VerificationDTO toDTO(Verification verification) {
        if (verification == null) return null;

        return VerificationDTO.builder()
                .id(verification.getId())
                .userId(verification.getUser() != null ? verification.getUser().getId() : null)
                .issueId(verification.getIssue() != null ? verification.getIssue().getId() : null)
                .createdAt(verification.getCreatedAt())
                .imageUrl(verification.getImageUrl())
                .note(verification.getNote())
                .build();
    }

    public static Verification toEntity(VerificationDTO dto, User user, Issue issue) {
        if (dto == null) return null;

        Verification verification = new Verification();
        verification.setId(dto.getId());
        verification.setUser(user);
        verification.setIssue(issue);
        verification.setImageUrl(dto.getImageUrl());
        verification.setNote(dto.getNote());
        verification.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        return verification;
    }
}