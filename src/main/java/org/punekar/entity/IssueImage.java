package org.punekar.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class IssueImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    @JsonBackReference
    private Issue issue;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType type; // BEFORE, AFTER

    public enum ImageType {
        BEFORE, // uploaded when issue is created
        AFTER   // uploaded during verification
    }
}