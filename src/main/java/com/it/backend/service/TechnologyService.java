package com.it.backend.service;

import com.it.backend.dto.response.TechnologyPayloadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TechnologyService {

    Map<String, TechnologyPayloadResponse> technologyMemory = new HashMap<>();

    public void update(String key, TechnologyPayloadResponse message){
        technologyMemory.putIfAbsent(key, message);
    }

    public Set<TechnologyPayloadResponse> getTechnologies(){
        return new HashSet<>(technologyMemory.values());
    }

}
