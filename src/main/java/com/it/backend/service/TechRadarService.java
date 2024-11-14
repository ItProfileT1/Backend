package com.it.backend.service;

import com.it.backend.dto.request.SkillTechRadarRequest;
import com.it.backend.dto.response.TechnologyPayloadResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TechRadarService {

    private final TechnologyService technologyService;

    private final SkillService skillService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Set<TechnologyPayloadResponse> getTechRadar() {
        sendRequests();
        return technologyService.getTechnologies();
    }

    @KafkaListener(topics = "technologyTopic", groupId = "technology")
    public void listenTechnologyMessage(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Payload @Valid TechnologyPayloadResponse message
    ) {
        technologyService.update(key, message);
    }


    public void sendRequests(){
        var messages = skillService.getAllSkillTechRadarRequests();
        for (SkillTechRadarRequest message : messages) {
            sendSkill(message.id().toString(), message);
        }
    }

    private void sendSkill(String key, SkillTechRadarRequest message) {
        kafkaTemplate.send("skillTopic", key, message);
    }

}
