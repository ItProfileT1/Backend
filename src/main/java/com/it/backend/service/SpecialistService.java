package com.it.backend.service;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.response.ProfileResponse;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
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
        //TODO specialistSkillsLevels поменять на specialistSkills
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
        throw new RuntimeException("Профиль уже существует");
        //TODO обработка ошибок
    }

    public Specialist updateSpecialist(Specialist specialist) {
        if (specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new EntityNotFoundException("specialist.not.found", specialist.getId());
    }

    public Set<ProfileResponse> findByPosition(String positionName) {
        Iterable<Specialist> specialists;
        if (positionName == null) {
            specialists = specialistRepository.findAll();
        } else {
            specialists = positionService.findPositionByName(positionName).getSpecialists();
        }
        Set<ProfileResponse> profileResponses = new HashSet<>();
        for (Specialist specialist : specialists) {
            profileResponses.add(getProfileBySpecialist(specialist));
        }
        return profileResponses;
    }

    public ProfileResponse findById(Long id) {
        var specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(("specialist.not.found"), id));
        return getProfileBySpecialist(specialist);
    }
}
