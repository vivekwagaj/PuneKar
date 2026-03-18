package org.punekar.controller;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.CommentDTO;

import org.punekar.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PostMapping
    public CommentDTO addComment(
            @PathVariable Long issueId,
            @RequestParam Long userId,
            @RequestParam(required = false) Long parentId,
            @RequestBody CommentDTO dto) {
        return commentService.addComment(userId, issueId, parentId, dto);
    }

    @GetMapping
    public List<CommentDTO> getComments(@PathVariable Long issueId) {
        return commentService.getCommentsByIssue(issueId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(
            @PathVariable Long issueId,
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
    }
}
