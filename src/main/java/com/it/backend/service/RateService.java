package com.it.backend.service;

import com.it.backend.entity.Rate;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {
    private final RateRepository rateRepository;

    public Rate findById(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("rate.not.found", id));
    }
}
