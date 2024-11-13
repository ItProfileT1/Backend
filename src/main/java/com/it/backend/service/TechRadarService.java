package com.it.backend.service;

import com.it.backend.dto.request.SkillTechRadarRequest;
import com.it.backend.dto.response.TechnologyPayloadResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TechRadarService {

//    @PostConstruct
//    void init(){
//        sendRequests();
//    }

    private final SkillService skillService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Set<TechnologyPayloadResponse> getTechRadar() {
        return null;
    }

    public void sendRequests(){
        var messages = skillService.getAllSkillTechRadarRequests();
        for (SkillTechRadarRequest message : messages) {
            sendSkill(message.name(), message);
        }
    }

    public void sendSkill(String key, SkillTechRadarRequest message) {
        kafkaTemplate.send("skillTopic", key, message);
    }

}
