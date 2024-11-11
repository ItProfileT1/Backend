package com.it.backend.service;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionMapper;
import com.it.backend.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public PositionResponse createPosition(PositionRequest request) {
        Position position = positionMapper.toPosition(request);
        if (positionRepository.existsByName(position.getName())) {
            //TODO сделать обработку ошибки в случае если должность уже существует
        }
        return positionMapper.toPositionResponse(positionRepository.save(position));
    }

    public PositionResponse findPositionById(Long id) {
        var position = findById(id);
        return positionMapper.toPositionResponse(position);
    }

    public Position findById(Long id){
        return positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
    }

    public Set<PositionResponse> findAllPositions() {
        Iterable<Position> positions = positionRepository.findAll();
        Set<PositionResponse> positionResponses = new HashSet<>();
        for (Position position : positions) {
            positionResponses.add(positionMapper.toPositionResponse(position));
        }
        return positionResponses;
    }

    public PositionResponse updatePosition(Long id, PositionRequest request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
        positionMapper.updatePosition(request, position);
        return positionMapper.toPositionResponse(positionRepository.save(position));
    }

    public void deletePosition(Long id) {
        positionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
    }

    public Set<PositionSkill> findAllPositionSkillsByPosition(Position position){
        return position.getPositionSkills();
    }
}
