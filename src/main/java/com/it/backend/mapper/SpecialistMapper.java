package com.it.backend.mapper;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.request.ProfileResponse;
import com.it.backend.entity.Specialist;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface SpecialistMapper {
    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assessmentProcesses", ignore = true)
    @Mapping(target = "assessorSkillRates", ignore = true)
    @Mapping(target = "position", ignore = true)
    //TODO specialistSkillsLevels заменить на specialistSkills
    @Mapping(target = "specialistSkillsLevels", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Specialist toSpecialist(ProfileRequest profileRequest);

    //@Mapping(source = "position", target = "positionResponse")
    //TODO сделать нормальный маппинг positionResponse и skillsResponses
    ProfileResponse toProfileResponse(Specialist specialist);
}
