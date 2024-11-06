package com.it.backend.mapper;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    @Mapping(source = "skillRequest.name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skillSpecialistsLevels", ignore = true)
    @Mapping(target = "skillRates", ignore = true)
    @Mapping(target = "positionsSkillsMinLevels", ignore = true)
    @Mapping(target = "levelsDescriptions", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "type", source = "type")
    Skill toSkill(SkillRequest skillRequest, Type type, Category category, Scale scale);

    default Skill toSkill(SpecialistSkill specialistSkill) {
        return specialistSkill.getSkill();
    }

    Set<Skill> toSkills(Iterable<SpecialistSkill> specialistSkills);

    SkillResponse toSkillResponse(Skill skill);

    Set<SkillResponse> toSkillResponses(Set<Skill> skills);
}
