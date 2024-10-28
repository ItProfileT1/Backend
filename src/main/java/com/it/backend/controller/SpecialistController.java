package com.it.backend.controller;

import com.it.backend.dto.request.ProfileRequest;
import com.it.backend.dto.request.ProfileResponse;
import com.it.backend.service.SpecialistService;
import com.it.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/specialists")
public class SpecialistController {

    private final SpecialistService specialistService;
    private final UserService userService;

    @PostMapping("profile")
    public ProfileResponse createProfile(@RequestBody ProfileRequest request){
        return specialistService.createProfile(request, userService.getCurrentUser());
    }

    @GetMapping("profile")
    public ProfileResponse findProfile(){
        return specialistService.getProfileByUser(userService.getCurrentUser());
    }
}
