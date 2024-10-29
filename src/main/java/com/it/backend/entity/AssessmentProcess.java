package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "assessment_processes", schema = "it_profile")
public class AssessmentProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deadline", nullable = false)
    private OffsetDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "assessee_id")
    private Specialist specialist;

    @OneToMany(mappedBy = "assessmentProcess")
    private Set<AssessorSkillRate> processRates;

    @OneToMany(mappedBy = "assessmentProcess")
    private Set<AssessmentProcessAssessorStatus> assessmentProcessAssessorStatuses;

    public boolean isExpired() {
        return deadline.isBefore(OffsetDateTime.now());
    }
}
