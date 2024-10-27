package com.it.backend.service;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.request.ProfileResponse;
import com.it.backend.entity.Specialist;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.SpecialistMapper;
import com.it.backend.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final UserService userService;
    private final PositionRepository positionRepository;
    private final SpecialistSkillService specialistSkillService;
    private final com.it.backend.repository.specialistRepository specialistRepository;

    @Transactional
    public ProfileResponse createProfile(ProfileRequest request){
        Specialist specialist = SpecialistMapper.INSTANCE.toSpecialist(request);
        request.positionId()
                .flatMap(positionRepository::findById)
                .ifPresent((specialist::setPosition));
        specialist.setUser(userService.getCurrentUser());
        specialist = saveSpecialist(specialist);
        specialist.setSpecialistSkillsLevels(specialistSkillService.create(
                specialist,
                request.skillsIds()
        ));
        specialist = updateSpecialist(specialist);
        return SpecialistMapper.INSTANCE.toProfileResponse(specialist);
        //TODO specialistSkillsLevels поменять на specialistSkills
    }

    public Specialist saveSpecialist(Specialist specialist){
        if (!specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new RuntimeException("Профиль уже существует");
    }

    public Specialist updateSpecialist(Specialist specialist){
        if (specialistRepository.existsByUser(specialist.getUser()))
            return specialistRepository.save(specialist);
        throw new EntityNotFoundException("Профиль не найден", specialist.getId());
    }
}
