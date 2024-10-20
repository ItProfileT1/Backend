package com.it.backend.controller;

import com.it.backend.dto.SpecialistDto;
import com.it.backend.service.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/specialists")
public class SpecialistController {

    private final SpecialistService specialistService;

    @PostMapping
    public ResponseEntity<Long> createSpecialist(@RequestBody SpecialistDto specialistDto){
        var specialistId = specialistService.createSpecialist(specialistDto);
        return specialistId.map(aLong -> ResponseEntity
                .created(URI.create("api/v1/specialists/" + specialistId.get()))
                .body(aLong)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<SpecialistDto> getSpecialist(@PathVariable Long id){
        var specialist = specialistService.getSpecialistById(id);
        return specialist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest()
                .build());
    }
}
