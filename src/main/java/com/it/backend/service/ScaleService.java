package com.it.backend.service;

import com.it.backend.entity.Scale;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.ScaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScaleService {

    private final ScaleRepository scaleRepository;

    public Scale findById(Long id){
        return scaleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("scale.not.found", id));
    }
}
