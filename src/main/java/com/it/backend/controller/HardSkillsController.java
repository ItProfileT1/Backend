package com.it.backend.controller;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.dto.StringDto;
import com.it.backend.service.HardSkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/hard_skills")
public class HardSkillsController {

    private final HardSkillsService hardSkillsService;

    @PostMapping
    public ResponseEntity<Long> createSHardSkill(@RequestBody NameDescriptionDto dto){
        var hardSkillId = hardSkillsService.createHardSkill(dto);
        return hardSkillId.map(aLong -> ResponseEntity
                .created(URI.create("api/v1/hard_skills/" + hardSkillId.get()))
                .body(aLong)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("{positionId}")
    public ResponseEntity<Object> attachHardSkillToPosition(@PathVariable Long positionId, @RequestBody StringDto hardSkillNameDto){
        var ok = hardSkillsService.attachToPosition(positionId, hardSkillNameDto);
        return ok.map(aLong -> ResponseEntity.ok().build()).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
