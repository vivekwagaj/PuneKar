package org.punekar.service;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.CommentDTO;
import org.punekar.entity.Comment;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.mapper.CommentMapper;
import org.punekar.repositories.CommentRepository;
import org.punekar.repositories.IssueRepository;
import org.punekar.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public CommentDTO addComment(Long userId, Long issueId, Long parentId, CommentDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        Comment parent = (parentId != null) ?
                commentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent comment not found")) : null;

        Comment comment = CommentMapper.toEntity(dto, user, issue, parent);
        Comment saved = commentRepository.save(comment);

        return CommentMapper.toDTO(saved);
    }

    public List<CommentDTO> getCommentsByIssue(Long issueId) {
        return commentRepository.findByIssue_Id(issueId).stream()
                .map(CommentMapper::toDTO)
                .toList();
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this comment");
        }

        commentRepository.delete(comment);
    }
}