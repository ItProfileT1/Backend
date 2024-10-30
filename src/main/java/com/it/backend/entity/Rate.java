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
@Table(name = "rates", schema = "it_profile")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "numeric_value")
    private int numericValue;

    @ManyToOne
    @JoinColumn(name = "scale_id")
    private Scale scale;

    @OneToMany(mappedBy = "rate")
    private Set<AssessorSkillRate> rateUsages;
}
