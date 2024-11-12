package com.it.backend.service;

import com.it.backend.dto.request.ScaleRequest;
import com.it.backend.dto.response.ScaleResponse;
import com.it.backend.entity.Scale;
import com.it.backend.exception.entity.DuplicateEntityException;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.ScaleMapper;
import com.it.backend.repository.ScaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ScaleService {

    private final ScaleRepository scaleRepository;
    private final ScaleMapper scaleMapper;

    public ScaleResponse createScale(ScaleRequest request) {
        if (scaleRepository.existsByName(request.name())) {
            throw new DuplicateEntityException("scale.already.exists", request.name());
        }
        Scale scale = scaleMapper.toScale(request);
        return scaleMapper.toScaleResponse(scaleRepository.save(scale));
    }

    public ScaleResponse findScaleById(Long id) {
        Scale scale = findById(id);
        return scaleMapper.toScaleResponse(scale);
    }

    public Set<ScaleResponse> findAllScales() {
        return scaleMapper.toScalesResponse(scaleRepository.findAll());
    }

    public ScaleResponse updateScale(Long id, ScaleRequest request) {
        Scale scale = findById(id);
        return scaleMapper.toScaleResponse(scaleMapper.updateScale(request, scale));
    }

    public void deleteScale(Long id) {
        Scale scale = findById(id);
        scaleRepository.delete(scale);
    }

    public Scale findById(Long id) {
        return scaleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("scale.not.found", id));
    }
}
