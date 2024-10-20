package com.it.backend.service;

import com.it.backend.dto.SpecialistDto;
import com.it.backend.entity.Specialist;
import com.it.backend.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;

    private SpecialistDto castToDto(Specialist specialist){
        return new SpecialistDto(
                specialist.getFio(),
                specialist.getBirthday(),
                specialist.getSex(),
                specialist.getCity()
        );
    }
    private Specialist castToEntity(SpecialistDto specialistDto){
        var specialist = new Specialist();
        specialist.setFio(specialistDto.fio());
        specialist.setBirthday(specialistDto.birthday());
        specialist.setSex(specialistDto.sex());
        specialist.setCity(specialistDto.city());
        return specialist;
    }

    public Optional<Long> createSpecialist(SpecialistDto specialistDto){
        if (specialistRepository.existsByFio(specialistDto.fio()))
            return Optional.empty();
        var specialist = castToEntity(specialistDto);
        return Optional.of(specialistRepository.save(specialist).getId());
    }

    public Optional<SpecialistDto> getSpecialistById(Long id) {
        var specialist = specialistRepository.findById(id);
        return specialist.map(this::castToDto);
    }


}
