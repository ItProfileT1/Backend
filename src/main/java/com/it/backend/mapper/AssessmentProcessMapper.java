package com.it.backend.mapper;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
