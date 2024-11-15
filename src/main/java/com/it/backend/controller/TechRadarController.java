package com.it.backend.controller;

import com.it.backend.dto.response.TechnologyPayloadResponse;
import com.it.backend.service.TechRadarService;
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
@RequestMapping("api/v1/tech_radar")
@Tag(name = "Техрадар")
public class TechRadarController {

    private final TechRadarService techRadarService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MASTER', 'ADMIN')")
    @Operation(summary = "Получение техрадара, доступно специалисту и руководителю")
    public Set<TechnologyPayloadResponse> getTechRadar(){
        return techRadarService.getTechRadar();
    }
}
