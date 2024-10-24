package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "specialists", schema = "it_profile")
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "specialist")
    private Set<SpecialistSkill> specialistSkillsLevels;

    @OneToMany(mappedBy = "assessee")
    private Set<AssessmentProcess> assessmentProcesses;

    @OneToMany(mappedBy = "assessor")
    private Set<AssessorSkillRate> assessorSkillRates;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

}