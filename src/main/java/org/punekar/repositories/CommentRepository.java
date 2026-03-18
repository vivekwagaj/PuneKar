package org.punekar.repositories;

import org.punekar.entity.Comment;
import org.punekar.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByIssue_Id(Long issueId);

    // Fetch all verifications for a given issue
    List<Comment> findByIssue_Id(Long issueId);

    // Optional: check if a user has already verified an issue
    boolean existsByIssue_IdAndUser_Id(Long issueId, Long userId);
}