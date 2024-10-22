package com.it.backend.controller;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.service.HardSkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/hard_skills")
public class HardSkillsController {

    private final HardSkillsService hardSkillsService;

    @PostMapping
    public ResponseEntity<Long> createSHardSkill(@RequestBody NameDescriptionDto dto){
        var positionId = hardSkillsService.createHardSkill(dto);
        return positionId.map(aLong -> ResponseEntity
                .created(URI.create("api/v1/positions/" + positionId.get()))
                .body(aLong)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
