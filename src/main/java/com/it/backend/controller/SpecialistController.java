package com.it.backend.controller;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.response.ProfileResponse;
import com.it.backend.service.SpecialistService;
import com.it.backend.service.TechRadarService;
import com.it.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/specialists")
@Tag(name = "Специалисты и их профили")
public class SpecialistController {
    private final SpecialistService specialistService;
    private final UserService userService;
    private final TechRadarService techRadarService;

    @PostMapping("profile")
    @Operation(summary = "Создание профиля, доступно только специалисту")
    @PreAuthorize("hasRole('USER')")
    public ProfileResponse createProfile(@RequestBody ProfileRequest request) {
        var response = specialistService.createProfile(request, userService.getCurrentUser());
        techRadarService.sendRequests();
        return response;
    }

    @GetMapping("profile")
    @Operation(summary = "Получение профиля, если он существует, доступно только специалисту")
    @PreAuthorize("hasRole('USER')")
    public ProfileResponse findProfile() {
        return specialistService.getProfileByUser(userService.getCurrentUser());
    }

    @GetMapping
    @Operation(summary = "Получение специалистов с определенной должностью, доступно p2p сервису")
    @PreAuthorize("hasAnyRole('P2P', 'ADMIN', 'MASTER')")
    public Set<ProfileResponse> findAllProfilesByPosition(
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) Integer level
    ) {
        return specialistService.findByPositionSkillLevel(position, skill, level);
    }

//    @GetMapping
//    @Operation(summary = "Получение специалистов по ключевому слову, доступно p2p сервису")
//    @PreAuthorize("hasAnyRole('P2P', 'ADMIN', 'MASTER')")
//    public Set<ProfileResponse> findByKeyWord(@RequestParam String search){
//        return specialistService.findByKeyWord(search);
//    }

    @GetMapping("{id}")
    @Operation(summary = "Получение специалиста по айди, доступно только администратору и p2p сервису")
    @PreAuthorize("hasAnyRole('P2P', 'ADMIN', 'MASTER')")
    public ProfileResponse findProfileByPosition(@PathVariable Long id) {
        return specialistService.getProfileById(id);
    }
}
