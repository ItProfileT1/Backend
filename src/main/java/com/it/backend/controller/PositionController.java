package com.it.backend.controller;

import com.it.backend.dto.NameAndDescriptionDto;
import com.it.backend.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/positions")
public class PositionController {

    private final PositionService positionService;

    @PostMapping("new")
    public void createPosition(@RequestBody NameAndDescriptionDto dto){
        positionService.createPosition(dto);
    }

    @PostMapping("hard_skills")
    public void addHardSkillsToPosition(){}
}
