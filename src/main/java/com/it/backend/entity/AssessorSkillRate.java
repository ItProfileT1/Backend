package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "assessors_skills_rates", schema = "it_profile")
public class AssessorSkillRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_process_id")
    private AssessmentProcess assessmentProcess;

    @ManyToOne
    @JoinColumn(name = "assessor_id")
    private Specialist assessor;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

}
