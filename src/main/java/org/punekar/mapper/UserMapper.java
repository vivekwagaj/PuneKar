package org.punekar.mapper;

import org.punekar.dto.UserDTO;
import org.punekar.entity.User;
import org.punekar.entity.Ward;

public class UserMapper {
    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;
        return User.builder().
                id(userDTO.getId()).
                firebaseUid(userDTO.getFirebaseUid()).
                displayName(userDTO.getDisplayName()).
                email(userDTO.getEmail()).
                photoUrl(userDTO.getPhotoUrl()).
                build();
    }

    public static User toEntity(UserDTO dto, Ward ward) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .firebaseUid(dto.getFirebaseUid())
                .displayName(dto.getDisplayName())
                .email(dto.getEmail())
                .photoUrl(dto.getPhotoUrl())
                .ward(ward)
                .build();
    }

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder().
                id(user.getId()).
                firebaseUid(user.getFirebaseUid()).
                displayName(user.getDisplayName()).
                email(user.getEmail()).
                photoUrl(user.getPhotoUrl()).
                wardId(user.getWard() != null ? user.getWard().getId() : null)
                .build();
    }
}
