package com.it.backend.controller;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.request.ProfileResponse;
import com.it.backend.service.SpecialistService;
import com.it.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/specialists")
@Tag(name = "Специалисты и их профили")
public class SpecialistController {
    //изменить профайл респонс
    //юзер дитейлс из методов оценки
    //Добавить методы круд к скиллам и профилям если это нужно фронту
    //Возможно добавить еще какие то методы для руководителя и админа, регистрация мастера
    //Прочитать у марины про возможность зарпрета каких то методово ролям
    private final SpecialistService specialistService;
    private final UserService userService;

    @PostMapping("profile")
    @Operation(summary = "Создание профиля, доступно только специалисту")
    @PreAuthorize("hasRole('USER')")
    public ProfileResponse createProfile(@RequestBody ProfileRequest request){
        return specialistService.createProfile(request, userService.getCurrentUser());
    }

    @GetMapping("profile")
    @Operation(summary = "Получение профиля, если он существует, доступно только специалисту")
    @PreAuthorize("hasRole('USER')")
    public ProfileResponse findProfile(){
        return specialistService.getProfileByUser(userService.getCurrentUser());
    }
}
