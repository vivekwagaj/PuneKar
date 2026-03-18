package org.punekar.service;

import org.punekar.dto.VerificationDTO;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.entity.Verification;
import org.punekar.mapper.VerificationMapper;
import org.punekar.repositories.IssueRepository;
import org.punekar.repositories.UserRepository;
import org.punekar.repositories.VerificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public VerificationService(VerificationRepository verificationRepository,
                               UserRepository userRepository,
                               IssueRepository issueRepository) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    public VerificationDTO createVerification(Long userId, Long issueId, String imageUrl, String note) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        if (verificationRepository.existsByIssue_IdAndUser_Id(issueId, userId)) {
            throw new RuntimeException("User has already verified this issue");
        }
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setIssue(issue);
        verification.setImageUrl(imageUrl);
        verification.setNote(note);
        verification.setCreatedAt(LocalDateTime.now());

        Verification saved = verificationRepository.save(verification);

        // Optional: auto-update issue status if verifications >= 2
        long verificationCount = verificationRepository.countByIssue_Id(issueId);
        if (verificationCount >= 2 && issue.getStatus() != Issue.IssueStatus.RESOLVED) {
            issue.setStatus(Issue.IssueStatus.RESOLVED);
            issue.setUpdatedAt(LocalDateTime.now());
            issueRepository.save(issue);
        }

        return VerificationMapper.toDTO(saved);
    }

    public List<VerificationDTO> getVerificationsByIssue(Long issueId) {
        return verificationRepository.findByIssue_Id(issueId).stream()
                .map(VerificationMapper::toDTO)
                .toList();
    }
}