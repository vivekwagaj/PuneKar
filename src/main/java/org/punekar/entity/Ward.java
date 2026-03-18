package org.punekar.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wards")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g. "Viman Nagar"

    private String zone;  //  PMC zone mapping

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Issue> issues;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private List<User> users;
}