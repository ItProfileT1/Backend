package com.it.backend.service;

import com.it.backend.entity.Position;
import com.it.backend.dto.IdNameDescriptionDto;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private final SpecialistRepository specialistRepository;

    public Optional<Long> createPosition(IdNameDescriptionDto dto){
        var specialist = specialistRepository.findById(dto.id());
        if (specialist.isEmpty())
            return Optional.empty();
        var position = new Position();
        position.setName(dto.name());
        position.setDescription(dto.description());
        positionRepository.save(position);
        var mySpecialist = specialist.get();
        mySpecialist.setPosition(position);
        specialistRepository.save(mySpecialist);
        return Optional.of(position.getId());
    }
}
