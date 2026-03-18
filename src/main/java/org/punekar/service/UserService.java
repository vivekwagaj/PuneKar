package org.punekar.service;

import org.punekar.dto.UserDTO;
import org.punekar.entity.User;
import org.punekar.entity.Ward;
import org.punekar.mapper.UserMapper;
import org.punekar.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WardRepository wardRepository;
    // Create user
    public UserDTO createUser(UserDTO userDTO) {
        Ward ward = null;
        if (userDTO.getWardId() != null) {
            ward = wardRepository.findById(userDTO.getWardId())
                    .orElseThrow(() -> new RuntimeException("Ward not found with id: " + userDTO.getWardId()));
        }

        User user = UserMapper.toEntity(userDTO, ward);
        return UserMapper.toDTO(userRepository.save(user));

    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get single user
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    // Update user
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existing -> {
            existing.setDisplayName(userDTO.getDisplayName());
            existing.setPhotoUrl(userDTO.getPhotoUrl());
            existing.setEmail(userDTO.getEmail());

            if (userDTO.getWardId() != null) {
                Ward ward = wardRepository.findById(userDTO.getWardId())
                        .orElseThrow(() -> new RuntimeException("Ward not found with id: " + userDTO.getWardId()));
                existing.setWard(ward);
            }

            return UserMapper.toDTO(userRepository.save(existing));
        });
    }

    // Delete user
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}
