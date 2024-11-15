package com.it.backend.service;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.response.ProfileResponse;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
import com.it.backend.exception.entity.DuplicateEntityException;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionMapper;
import com.it.backend.mapper.SpecialistMapper;
import com.it.backend.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistSkillService specialistSkillService;
    private final SpecialistRepository specialistRepository;
    private final PositionService positionService;
    private final SpecialistMapper specialistMapper;
    private final PositionMapper positionMapper;
    private final SkillService skillService;

    @Transactional
    public ProfileResponse createProfile(ProfileRequest request, User user) {
        Specialist specialist = specialistMapper.toSpecialist(request);
        request.positionId()
                .map(positionService::findById)
                .ifPresent((specialist::setPosition));
        specialist.setUser(user);
        specialist = saveSpecialist(specialist);
        specialist.setSpecialistSkillsLevels(specialistSkillService.create(
                specialist,
                request.skillsIds()
        ));
        specialist = updateSpecialist(specialist);
        return getProfileBySpecialist(specialist);
    }

    public Specialist findById(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("specialist.not.found", id));
    }

    public ProfileResponse getProfileByUser(User user) {
        var specialist = specialistRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("specialist.not.found", user.getId()));
        return getProfileBySpecialist(specialist);
    }

    private ProfileResponse getProfileBySpecialist(Specialist specialist) {
        var positionResponse = positionMapper.toPositionResponse(specialist.getPosition(), null);
        var skillLevelResponses = specialistSkillService.getSkillLevelsBySpecialist(specialist);
        return specialistMapper.toProfileResponse(specialist, positionResponse, skillLevelResponses);
    }

    public Specialist saveSpecialist(Specialist specialist) {
        if (!specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new DuplicateEntityException("specialist.already.exists", specialist.getId());
    }

    public Specialist updateSpecialist(Specialist specialist) {
        if (specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new EntityNotFoundException("specialist.not.found", specialist.getId());
    }

    public Iterable<Specialist> findByPosition(String positionName) {
        if (positionName == null) {
            return specialistRepository.findAll();
        }
        return positionService.findPositionByName(positionName).getSpecialists();
    }

    public ProfileResponse getProfileById(Long id) {
        return getProfileBySpecialist(findById(id));
    }

    public Iterable<Specialist> findByPositionSkill(String position, String skill) {
        var specialists = findByPosition(position);
        if (skill == null){
            return specialists;
        }
        Set<Specialist> filteredSpecialists = new HashSet<>();
        for (Specialist specialist : specialists) {
            if (skillService.existsBySpecialist(specialist, skill)){
                filteredSpecialists.add(specialist);
            }
        }
        return filteredSpecialists;
    }

    public Set<ProfileResponse> findByPositionSkillLevel(String position, String skill, Integer levelValue){
        var specialists = findByPositionSkill(position, skill);
        Set<Specialist> filteredSpecialists = new HashSet<>();
        if (levelValue == null){
            specialists.forEach(filteredSpecialists::add);
        }
        else {
            for (Specialist specialist : specialists) {
                var levelBySkill = skillService.getLevelBySpecialistSkill(specialist, skill);
                if (levelBySkill.getNumericValue() >= levelValue){
                    filteredSpecialists.add(specialist);
                }
            }
        }
        Set<ProfileResponse> profileResponses = new HashSet<>();
        for (Specialist specialist : filteredSpecialists) {
            profileResponses.add(getProfileBySpecialist(specialist));
        }
        return profileResponses;
    }
}
