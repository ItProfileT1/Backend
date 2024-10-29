package com.it.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "skills", schema = "it_profile")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "rate_scale_id")
    private Scale scale;

    @OneToMany(mappedBy = "skill") // TODO интеграция
    private Set<SpecialistSkill> skillSpecialistsLevels;

    @OneToMany(mappedBy = "skill")
    private Set<AssessorSkillRate> skillRates;

    @OneToMany(mappedBy = "skill")
    private Set<PositionSkill> positionsSkillsMinLevels;

    @OneToMany(mappedBy = "skill")
    private Set<SkillLevel> levelsDescriptions;
}