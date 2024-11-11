package com.it.backend.mapper;

import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.AssessmentProcessAssessorStatus;
import com.it.backend.entity.Status;
import com.it.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssessmentProcessAssessorStatusMapper {

    @Mapping(target = "id", ignore = true)
    AssessmentProcessAssessorStatus toAssessmentProcessAssessorStatus(
            AssessmentProcess assessmentProcess, User assessor, Status status);

}
