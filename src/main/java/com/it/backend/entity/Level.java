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
@Table(name = "skill_levels", schema = "it_profile")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "numeric_value", nullable = false)
    private int numericValue;

    @OneToMany(mappedBy = "minLevel")
    private Set<PositionSkill> levelPositionsSkills;

    @OneToMany(mappedBy = "level")
    private Set<SpecialistSkill> levelSpecialistsSkills;
}
