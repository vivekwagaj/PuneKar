package org.punekar.service;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.IssueDTO;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.mapper.IssueMapper;
import org.punekar.repositories.IssueRepository;
import org.punekar.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;


    public IssueDTO createIssue(IssueDTO dto) {
        Issue issue = new Issue();
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setCategory(dto.getCategory());
        issue.setStatus(Issue.IssueStatus.OPEN);

        User creator = userRepository.findById(Long.valueOf(dto.getCreatedById()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        issue.setUser(creator);

        Issue saved = issueRepository.save(issue);
        return IssueMapper.toDTO(saved);
    }


    public List<IssueDTO> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(IssueMapper::toDTO)
                .toList();
    }


    public IssueDTO getIssueById(Long id) {
        return issueRepository.findById(id)
                .map(IssueMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }


    public IssueDTO updateStatus(Long id, IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setStatus(Issue.IssueStatus.valueOf(issueDTO.getStatus()));
        return IssueMapper.toDTO(issueRepository.save(issue));
    }

    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }
}