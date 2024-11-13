package com.it.backend.controller;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.service.SkillService;
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

    @GetMapping
    @Operation(summary = "Получение всех навыков, доступно только администратору и специалисту")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<SkillResponse> findAllSkills(@RequestParam(required = false) String type) {
        return skillService.findAll(type);
    }

    @PostMapping
    @Operation(summary = "Создание нового навыка, доступно только администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public SkillResponse createSkill(@RequestBody SkillRequest request) {
        return skillService.create(request);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение навыков по айди, доступно только администратору и специалисту")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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
