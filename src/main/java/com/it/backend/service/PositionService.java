package com.it.backend.service;

import com.it.backend.entity.Position;
import com.it.backend.dto.IdNameDescriptionDto;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.SpecialistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private final SpecialistRepository specialistRepository;

    private IdNameDescriptionDto castToDto(Position position){
        return new IdNameDescriptionDto(
                position.getId(),
                position.getName(),
                position.getDescription()
        );
    }

    @Transactional
    public Optional<Long> createPosition(IdNameDescriptionDto dto){
        var specialist = specialistRepository.findById(dto.id());
        if (specialist.isEmpty())
            return Optional.empty();
        var mySpecialist = specialist.get();
        if (mySpecialist.getPosition() != null)
            return Optional.empty();
        var position = new Position();
        position.setName(dto.name());
        position.setDescription(dto.description());
        position = positionRepository.save(position);
        mySpecialist.setPosition(position);
        specialistRepository.save(mySpecialist);
        return Optional.of(position.getId());
    }

    public Optional<IdNameDescriptionDto> getBySpecialistId(Long id){
        var optionalSpecialist = specialistRepository.findById(id);
        if (optionalSpecialist.isEmpty())
            return Optional.empty();
        var specialist = optionalSpecialist.get();
        var position = specialist.getPosition();
        if (position == null)
            return Optional.empty();
        return Optional.of(castToDto(position));
    }
}
