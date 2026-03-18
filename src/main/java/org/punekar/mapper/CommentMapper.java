package org.punekar.mapper;

import org.punekar.dto.CommentDTO;
import org.punekar.entity.Comment;
import org.punekar.entity.User;
import org.punekar.entity.Issue;

import java.util.stream.Collectors;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;

        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setUserId(comment.getUser() != null ? comment.getUser().getId() : null);
        dto.setContent(comment.getContent());
        dto.setParentId(comment.getParent() != null ? comment.getParent().getId() : null);
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public static Comment toEntity(CommentDTO dto, User user, Issue issue, Comment parent) {
        if (dto == null) return null;

        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setUser(user);
        comment.setIssue(issue);
        comment.setParent(parent);
        comment.setContent(dto.getContent());
        comment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now());
        return comment;
    }
}