package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "city")
    private String city;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "specialist")
    private Set<SpecialistSkill> specialistSkillsLevels;
    //TODO тут стоит назвать specialistSkills

    @OneToMany(mappedBy = "specialist")
    private Set<AssessmentProcess> assessmentProcesses;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

}