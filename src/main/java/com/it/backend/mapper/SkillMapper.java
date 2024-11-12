package com.it.backend.mapper;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    @Mapping(source = "skillRequest.name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skillSpecialistsLevels", ignore = true)
    @Mapping(target = "skillRates", ignore = true)
    @Mapping(target = "skillLevels", ignore = true)
    Skill toSkill(SkillRequest skillRequest, Type type, Category category, Scale scale);

    default Skill toSkill(SpecialistSkill specialistSkill) {
        return specialistSkill.getSkill();
    }

    Set<Skill> toSkills(Iterable<SpecialistSkill> specialistSkills);

    SkillResponse toSkillResponse(Skill skill);

    Set<SkillResponse> toSkillResponses(Iterable<Skill> skills);
}
