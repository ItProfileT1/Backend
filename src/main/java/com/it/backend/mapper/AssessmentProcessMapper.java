package com.it.backend.mapper;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AssessmentProcessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "processRates", ignore = true)
    @Mapping(target = "assessmentProcessAssessorStatuses", ignore = true)
    AssessmentProcess toAssessmentProcess(AssessmentProcessRequest assessmentProcessRequest,
                                          Specialist specialist, User creator,
                                          OffsetDateTime createdAt);

    AssessmentProcessResponse toAssessmentProcessResponse(AssessmentProcess assessmentProcess);

    Set<AssessmentProcessResponse> toAssessmentProcessesResponse(Iterable<AssessmentProcess> assessmentProcesses);

}
