package com.it.backend.controller;

import com.it.backend.dto.response.SkillResponse;
import com.it.backend.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/skills")
@Tag(name = "Навыки")
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    @Operation(summary = "Получение всех навыков, доступно только администратору и специалисту")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<SkillResponse> findAllSkills(){
        return skillService.findAll();
    }

//    @PostMapping
//    public Set<SkillResponse> createSkill()
}
