package org.punekar.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private Long id;
    private String firebaseUid;
    private String email;
    private String displayName;
    private String photoUrl;
    private Long wardId;
}