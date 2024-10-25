package com.it.backend.controller;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.request.PositionSkillsRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.dto.response.PositionSkillResponse;
import com.it.backend.service.PositionService;
import com.it.backend.service.PositionSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/positions")
public class PositionController {

    private final PositionService positionService;
    private final PositionSkillService positionSkillService;

    @PostMapping
    public PositionResponse createPosition(@RequestBody PositionRequest request) {
        return positionService.createPosition(request);
    }

    @GetMapping("{id}")
    public PositionResponse findPositionById(@PathVariable Long id) {
        return positionService.findPositionById(id);
    }

    @PostMapping("{id}")
    public PositionResponse updatePosition(@PathVariable Long id, @RequestBody PositionRequest request) {
        return positionService.updatePosition(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/skills")
    public Set<PositionSkillResponse> addSkills(@PathVariable Long id, @RequestBody PositionSkillsRequest request) {
        return positionSkillService.addSkills(id, request);
    }
}
