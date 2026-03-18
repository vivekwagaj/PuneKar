package org.punekar.repositories;

import org.punekar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    java.util.Optional<User> findByFirebaseUid(String firebaseUid);
}