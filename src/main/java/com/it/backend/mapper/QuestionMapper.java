package com.it.backend.mapper;

import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.entity.Rate;
import com.it.backend.entity.Skill;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, RateMapper.class})
public interface QuestionMapper {

    QuestionResponse toQuestionResponse(Skill skill, Set<Rate> rates);
}
