package com.it.backend.mapper;

import com.it.backend.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AssessorSkillRateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "comment", ignore = true)
    AssessorSkillRate toAssessorSkillRate(AssessmentProcess assessmentProcess, User assessor, Skill skill);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "rate", target = "rate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAssessorSkillRate(@MappingTarget AssessorSkillRate assessorSkillRate, Rate rate, String comment);
}
