package com.it.backend.controller;

import com.it.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("admin")
    public String getAdminPage(){
        return "Hello admin";
    }

    @GetMapping("user")
    public String getUserPage(){
        return "Hello user";
    }

    @GetMapping("principal")
    public String getUserName() {
        return userService.getCurrentUser().getUsername();
    }
}
