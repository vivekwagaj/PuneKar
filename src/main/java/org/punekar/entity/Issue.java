package org.punekar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "issues")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private IssueStatus status; // OPEN, IN_PROGRESS, RESOLVED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<IssueImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Verification> verifications = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    @JsonBackReference
    private Ward ward;

    public enum IssueStatus {
        OPEN,        // newly created
        IN_PROGRESS, // acknowledged / work started
        RESOLVED,    // verified by 2+ users
        REJECTED     // closed as invalid / duplicate
    }
}