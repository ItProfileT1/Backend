package com.it.backend.controller;

import com.it.backend.dto.IdNameDescriptionDto;
import com.it.backend.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/positions")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<Long> createPosition(@RequestBody IdNameDescriptionDto dto){
        var positionId = positionService.createPosition(dto);
        return positionId.map(aLong -> ResponseEntity
                .created(URI.create("api/v1/positions/" + positionId.get()))
                .body(aLong)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

//    @PostMapping("hard_skills")
//    public void addHardSkillsToPosition(){}
}
