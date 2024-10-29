package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "assessment_process_assessor_status", schema = "it_profile")
public class AssessmentProcessAssessorStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_process_id")
    private AssessmentProcess assessmentProcess;

    @ManyToOne
    @JoinColumn(name = "assessor_id")
    private User assessor;

    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isCompleted() {
        return status == Status.COMPLETED;
    }
}
