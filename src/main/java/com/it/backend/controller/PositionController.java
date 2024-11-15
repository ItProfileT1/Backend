package com.it.backend.controller;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/positions")
@Tag(name = "Должности")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание должности, доступно только администратору")
    public PositionResponse createPosition(@RequestBody PositionRequest request) {
        return positionService.createPosition(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'P2P')")
    @Operation(summary = "Получение должности по айди, доступно авторизованным пользователям")
    public PositionResponse findPositionById(@PathVariable Long id) {
        return positionService.findPositionById(id);
    }

    @GetMapping()
    @Operation(summary = "Получение всех должностей, доступно авторизованным пользователям")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'P2P')")
    public Set<PositionResponse> findAllPositions() {
        return positionService.findAllPositions();
    }

    @PostMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Изменение должности по айди, доступно только администратору")
    public PositionResponse updatePosition(@PathVariable Long id, @RequestBody PositionRequest request) {
        return positionService.updatePosition(id, request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удаление должности по айди, доступно только администратору")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{positionId}/skills")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Добавление навыков к должности по айди, доступно только администратору")
    public PositionResponse attachSkills(@PathVariable Long positionId, @RequestBody Set<Long> skillsIds) {
        return positionService.attachSkills(positionId, skillsIds);
    }
}
