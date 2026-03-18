package org.punekar.controller;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.IssueDTO;

import org.punekar.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO issueDTO) {
        // create new issue
        return ResponseEntity.ok(issueService.createIssue(issueDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable Long id) {
        // fetch issue by ID
        return ResponseEntity.ok(issueService.getIssueById(id));
    }

    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues() {
        // list all issues
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable Long id, @RequestBody IssueDTO issueDTO) {
        // update issue details
        return ResponseEntity.ok(issueService.updateStatus(id,issueDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long id) {
        // delete issue
        issueService.deleteIssue(id);
        return ResponseEntity.ok("Issue deleted successfully");
    }
}
