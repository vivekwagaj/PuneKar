package org.punekar.service;

import lombok.RequiredArgsConstructor;
import org.punekar.dto.IssueDTO;
import org.punekar.dto.UserDTO;
import org.punekar.entity.Issue;
import org.punekar.entity.User;
import org.punekar.entity.Ward;
import org.punekar.mapper.IssueMapper;
import org.punekar.mapper.UserMapper;
import org.punekar.repositories.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardService {
    @Autowired
    private WardRepository wardRepository;


    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }


    public Ward getWardById(Long id) {
        return wardRepository.getById(id);
    }


    public Ward createWard(Ward ward) {
        return wardRepository.save(ward);
    }

    public void deleteWard(Long id) {
        wardRepository.deleteById(id);
    }

    public Ward updateWard(Long id, Ward updatedWard) {
        return wardRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedWard.getName());
                    existing.setZone(updatedWard.getZone());
                    return wardRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Ward not found with id " + id));
    }

    public List<UserDTO> getUsersByWardId(Long wardId) {
        return wardRepository.findById(wardId)
                .map(ward -> ward.getUsers().stream()
                        .map(UserMapper::toDTO)
                        .toList()
                )
                .orElseThrow(() -> new RuntimeException("Ward not found"));
    }

    public List<IssueDTO> getIssuesByWardId(Long wardId) {
        return wardRepository.findById(wardId)
                .map(ward -> ward.getIssues().stream()
                        .map(IssueMapper::toDTO).toList()
                )
                .orElseThrow(() -> new RuntimeException("Ward not found"));
    }
}
