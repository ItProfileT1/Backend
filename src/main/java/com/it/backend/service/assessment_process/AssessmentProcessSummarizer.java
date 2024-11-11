package com.it.backend.service.assessment_process;

import com.it.backend.entity.*;
import com.it.backend.mapper.SpecialistSkillMapper;
import com.it.backend.repository.AssessorSkillRateRepository;
import com.it.backend.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssessmentProcessSummarizer {
    private final LevelRepository levelRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final SpecialistSkillMapper specialistSkillMapper;

    public Set<SpecialistSkill> summarizeResults(AssessmentProcess assessmentProcess) {
        MultiValuedMap<Skill, Rate> skillRates = getAssessmentProcessSkillRates(assessmentProcess);

        Set<SpecialistSkill> specialistSkills = new HashSet<>();
        for (Skill skill : skillRates.keySet()) {
            long numberOfRates = skillRates.get(skill).size();
            long maxRateSum = skill.getScale().getNumberOfSignificantRates() * numberOfRates;
            long actualRateSum = 0;
            for (Rate rate : skillRates.get(skill)) {
                actualRateSum += rate.getNumericValue();
            }
            long levelNumericValue;
            if (actualRateSum < 0) {
                levelNumericValue = -1;
            } else {
                long avgActualRate = actualRateSum / numberOfRates;
                long numberOfLevels = levelRepository.count();
                levelNumericValue = avgActualRate / (maxRateSum / numberOfLevels + 1);
            }
            Level level = levelRepository.findByNumericValue((int) levelNumericValue);

            specialistSkills.add(specialistSkillMapper.toSpecialistSkill(
                    assessmentProcess.getSpecialist(), skill, level, LocalDate.now()));
        }
        return specialistSkills;
    }

    private MultiValuedMap<Skill, Rate> getAssessmentProcessSkillRates(AssessmentProcess assessmentProcess) {
        Set<AssessorSkillRate> assessorSkillRates = assessorSkillRateRepository
                .findAllAssessorSkillRatesByAssessmentProcess(assessmentProcess, Status.COMPLETED);

        MultiValuedMap<Skill, Rate> skillRates = new HashSetValuedHashMap<>();
        for (AssessorSkillRate assessorSkillRate : assessorSkillRates) {
            skillRates.put(assessorSkillRate.getSkill(), assessorSkillRate.getRate());
        }
        return skillRates;
    }
}
