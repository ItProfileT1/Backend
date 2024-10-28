package com.it.backend.service;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.request.ProfileResponse;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.mapper.SpecialistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistSkillService specialistSkillService;
    private final com.it.backend.repository.specialistRepository specialistRepository;
    private final PositionService positionService;

    @Transactional
    public ProfileResponse createProfile(ProfileRequest request, User user){
        Specialist specialist = SpecialistMapper.INSTANCE.toSpecialist(request);
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

    public ProfileResponse getProfileByUser(User user){
        var specialist = specialistRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("specialist.not.found", user.getId()));
        return getProfileBySpecialist(specialist);
    }

    private ProfileResponse getProfileBySpecialist(Specialist specialist) {
        var positionResponse = PositionMapper.INSTANCE.toPositionResponse(specialist.getPosition());
        var skills = specialistSkillService.getSkillsBySpecialist(specialist);
        var skillResponses = SkillMapper.INSTANCE.toSkillResponses(skills);
        return SpecialistMapper.INSTANCE.toProfileResponse(specialist, positionResponse, skillResponses);
    }

    public Specialist saveSpecialist(Specialist specialist){
        if (!specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new RuntimeException("Профиль уже существует");
        //TODO обработка ошибок
    }

    public Specialist updateSpecialist(Specialist specialist){
        if (specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new EntityNotFoundException("specialist.not.found", specialist.getId());
    }
}