package org.punekar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verifications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"issue_id", "user_id"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @JsonBackReference
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String note;

    private LocalDateTime createdAt;
}
