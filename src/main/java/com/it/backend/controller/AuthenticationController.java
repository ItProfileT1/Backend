package com.it.backend.controller;

import com.it.backend.dto.request.SignInRequest;
import com.it.backend.dto.request.SignUpRequest;
import com.it.backend.dto.response.IdResponse;
import com.it.backend.dto.response.JwtAuthenticationResponse;
import com.it.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@Tag(name = "Аутентификация")
public class AuthenticationController {
    //TODO назвать таблицы связки в бд 1:1 например вместо positions_skills -> position_skills

    private final AuthenticationService authenticationService;

    @PostMapping("sign-up")
    @Operation(summary = "Регистрация пользователя, доступно только администратору")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IdResponse> signUpUser(@RequestBody @Validated SignUpRequest request) {
        //TODO сделать обработку ошибок (пользователь с таким юзернеймом уже существует)
        var id = authenticationService.signUp(request);
        return ResponseEntity.ok(new IdResponse(id));
    }

    @PostMapping("sign-in")
    @Operation(summary = "Авторизация пользователя")
    public JwtAuthenticationResponse signIn(@RequestBody @Validated SignInRequest request) {
        //TODO сделать обработку ошибок (неверный юзернейм или пароль)
        return authenticationService.signIn(request);
    }
}
