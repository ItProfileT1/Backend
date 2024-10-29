package com.it.backend.mapper;

import com.it.backend.dto.response.SkillLevelResponse;
import com.it.backend.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, LevelMapper.class})
public interface SpecialistSkillMapper {
    SpecialistSkillMapper INSTANCE = Mappers.getMapper(SpecialistSkillMapper.class);

    @Mapping(target = "id", ignore = true)
    SpecialistSkill toSpecialistSkill(Specialist specialist, Skill skill, Level level, LocalDate date);

    @Mapping(source = "specialistSkill.id", target = "id")
    @Mapping(source = "specialistSkill.skill", target = "skillResponse")
    @Mapping(source = "specialistSkill.level", target = "levelResponse")
    SkillLevelResponse toSkillLevelResponse(SpecialistSkill specialistSkill, SkillLevel skillLevel);

    Set<SkillLevelResponse> toSkillLevelsResponse(Set<SpecialistSkill> specialistSkills);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpecialistSkill(@MappingTarget SpecialistSkill specialistSkill, LocalDate date, Level level);
}
