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
@Table(name = "scales", schema = "it_profile")
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "scale")
    private Set<Rate> rates;

    @OneToMany(mappedBy = "scale")
    private Set<Skill> skills;

    public int getNumberOfSignificantRates() {
        int counter = 0;
        for (Rate rate : rates) {
            if (rate.getNumericValue() != 0) {
                counter++;
            }
        }
        return counter;
    }
}
