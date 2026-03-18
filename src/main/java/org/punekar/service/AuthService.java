package org.punekar.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.punekar.entity.User;
import org.punekar.entity.Ward;
import org.punekar.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final FirebaseTokenVerifier tokenVerifier;
    private final UserRepository userRepository;

    public User verifyTokenAndLogin(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = tokenVerifier.verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        
        return userRepository.findByFirebaseUid(uid).orElseGet(() -> {
            User newUser = User.builder()
                    .firebaseUid(uid)
                    .email(decodedToken.getEmail() != null ? decodedToken.getEmail() : "no-email@pune.kar")
                    .displayName(decodedToken.getName() != null ? decodedToken.getName() : "Citizen")
                    .photoUrl(decodedToken.getPicture())
                    .role(User.Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();
            return userRepository.save(newUser);
        });
    }
}
