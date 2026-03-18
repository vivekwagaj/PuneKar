package org.punekar.controller;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.VerificationDTO;
import org.punekar.entity.Vote;
import org.punekar.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verifications")
@RequiredArgsConstructor
public class VerificationController {

    @Autowired
    private final VerificationService verificationService;

    @PostMapping
    public ResponseEntity<VerificationDTO> createVerification(
            @RequestParam Long userId,
            @RequestParam Long issueId,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String note) {
        return ResponseEntity.ok(
                verificationService.createVerification(userId, issueId, imageUrl, note)
        );
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<VerificationDTO>> getVerificationsByIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(verificationService.getVerificationsByIssue(issueId));
    }
}