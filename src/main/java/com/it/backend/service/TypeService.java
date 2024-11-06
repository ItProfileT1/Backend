package com.it.backend.service;

import com.it.backend.entity.Type;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public Type findById(Long id){
        return typeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("type.not.found", id));
    }

    public Type findByName(String name){
        return typeRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(String.format("type.%s.not.found", name), 0L));
    }

}
