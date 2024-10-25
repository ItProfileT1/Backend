package com.it.backend.service;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.entity.Position;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionMapper;
import com.it.backend.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionResponse createPosition(PositionRequest request) {
        Position position = PositionMapper.INSTANCE.toPosition(request);
        if (positionRepository.existsByName(position.getName())) {
        }
        return PositionMapper.INSTANCE.toPositionResponse(positionRepository.save(position));
    }

    public PositionResponse findPositionById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
        return PositionMapper.INSTANCE.toPositionResponse(position);
    }

    public PositionResponse updatePosition(Long id, PositionRequest request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
        PositionMapper.INSTANCE.updatePosition(request, position);
        return PositionMapper.INSTANCE.toPositionResponse(positionRepository.save(position));
    }

    public void deletePosition(Long id) {
        positionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
    }
}
