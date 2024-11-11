package com.it.backend.mapper;

import com.it.backend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AssessorSkillRateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rate", ignore = true)
    AssessorSkillRate toAssessorSkillRate(AssessmentProcess assessmentProcess, User assessor, Skill skill);

    @Mapping(target = "id", ignore = true)
    AssessorSkillRate toAssessorSkillRate(AssessmentProcess assessmentProcess, User assessor, Skill skill, Rate rate);
}
