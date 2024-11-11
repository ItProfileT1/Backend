package com.it.backend.mapper;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.response.ProfileResponse;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Specialist;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface SpecialistMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assessmentProcesses", ignore = true)
    @Mapping(target = "position", ignore = true)
    //TODO specialistSkillsLevels заменить на specialistSkills
    @Mapping(target = "specialistSkillsLevels", ignore = true)
    Specialist toSpecialist(ProfileRequest profileRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "specialist.name", target = "name")
    ProfileResponse toProfileResponse(Specialist specialist, PositionResponse positionResponse, Set<SkillResponse> skillResponses);
}
