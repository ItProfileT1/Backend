package com.it.backend.controller;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.service.SkillService;
import com.it.backend.service.TechRadarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/skills")
@Tag(name = "Навыки")
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;
    private final TechRadarService techRadarService;

    @GetMapping
    @Operation(summary = "Получение всех навыков, доступно авторизированным пользователям")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'P2P')")
    public Set<SkillResponse> findAllSkills(@RequestParam(required = false) String type) {
        return skillService.findAll(type);
    }

    @PostMapping
    @Operation(summary = "Создание нового навыка, доступно только администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public SkillResponse createSkill(@RequestBody SkillRequest request) {
        var skillResponse = skillService.create(request);
        techRadarService.sendRequests();
        return skillResponse;
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение навыков по айди, доступно авторизованным пользователям")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'P2P')")
    public SkillResponse findById(@PathVariable Long id) {
        return skillMapper.toSkillResponse(skillService.findById(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление навыка по айди, доступно только администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
