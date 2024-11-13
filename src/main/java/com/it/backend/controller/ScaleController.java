package com.it.backend.controller;

import com.it.backend.dto.request.ScaleRequest;
import com.it.backend.dto.response.ScaleResponse;
import com.it.backend.service.ScaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/scales")
@Tag(name = "Шкалы оценивания навыков")
public class ScaleController {

    private final ScaleService scaleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание шкалы оценивания, доступно только администратору")
    public ScaleResponse createPosition(@RequestBody ScaleRequest request) {
        return scaleService.createScale(request);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Получение шкалы оценивания по id, доступно только администратору и специалисту")
    public ScaleResponse findPositionById(@PathVariable Long id) {
        return scaleService.findScaleById(id);
    }

    @GetMapping()
    @Operation(summary = "Получение всех шкал оценивания, доступно только администратору и специалисту")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<ScaleResponse> findAllScales() {
        return scaleService.findAllScales();
    }

    @PostMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Изменение шкалы оценивания по id, доступно только администратору")
    public ScaleResponse updateScale(@PathVariable Long id, @RequestBody ScaleRequest request) {
        return scaleService.updateScale(id, request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удаление шкалы оценивания по id, доступно только администратору")
    public ResponseEntity<Void> deleteScale(@PathVariable Long id) {
        scaleService.deleteScale(id);
        return ResponseEntity.noContent().build();
    }
}
