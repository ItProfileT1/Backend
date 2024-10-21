package com.it.backend.controller;

import com.it.backend.dto.IdNameDescriptionDto;
import com.it.backend.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("specialist/{id}")
    public ResponseEntity<IdNameDescriptionDto> getPositionBySpecialistId(@PathVariable Long id){
        var dto = positionService.getBySpecialistId(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest()
                        .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<IdNameDescriptionDto> getPositionById(@PathVariable Long id){
        var dto = positionService.getBySpecialistId(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest()
                        .build());
    }
}
