package com.it.backend.mapper;

import com.it.backend.dto.response.ResultResponse;
import com.it.backend.dto.response.SkillLevelResponse;
import com.it.backend.entity.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, LevelMapper.class})
public interface SpecialistSkillMapper {

    @Mapping(target = "id", ignore = true)
    SpecialistSkill toSpecialistSkill(Specialist specialist, Skill skill, Level level, LocalDate date);

    @Mapping(source = "specialistSkill.id", target = "id")
    @Mapping(source = "specialistSkill.skill", target = "skill")
    @Mapping(source = "specialistSkill.level", target = "level")
    SkillLevelResponse toSkillLevelResponse(SpecialistSkill specialistSkill, SkillLevel skillLevel);

    @Mapping(source = "specialistSkill.skill", target = "skill")
    @Mapping(source = "specialistSkill.level", target = "level")
    ResultResponse toResultResponse(SpecialistSkill specialistSkill, SkillLevel skillLevel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateSpecialistSkill(@MappingTarget SpecialistSkill specialistSkill, Level level, LocalDate date);
}
