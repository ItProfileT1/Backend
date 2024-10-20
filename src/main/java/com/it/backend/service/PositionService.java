package com.it.backend.service;

import com.it.backend.entity.Position;
import com.it.backend.dto.NameAndDescriptionDto;
import com.it.backend.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public void createPosition(NameAndDescriptionDto dto){
        var position = new Position();
        position.setName(dto.name());
        position.setDescription(dto.description());
        positionRepository.save(position);
    }
}
