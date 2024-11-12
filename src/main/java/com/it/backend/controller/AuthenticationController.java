package com.it.backend.controller;

import com.it.backend.dto.request.SignInRequest;
import com.it.backend.dto.request.SignUpRequest;
import com.it.backend.dto.request.TokenGenerationRequest;
import com.it.backend.dto.response.JwtAuthenticationResponse;
import com.it.backend.dto.response.RoleResponse;
import com.it.backend.dto.response.TokenAuthenticationResponse;
import com.it.backend.dto.response.UserResponse;
import com.it.backend.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseEntity<UserResponse> signUpUser(@RequestBody @Validated SignUpRequest request) {
        //TODO сделать обработку ошибок (пользователь с таким юзернеймом уже существует)
        var userResponse = authenticationService.signUp(request);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("sign-in")
    @Operation(summary = "Авторизация пользователя")
    public JwtAuthenticationResponse signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные для входа", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SignInRequest.class),
                            examples = {
                                    @ExampleObject(name = "Admin Login", value = "{\"username\": \"admin\", \"password\": \"admin\"}"),
                                    @ExampleObject(name = "User Login", value = "{\"username\": \"joe\", \"password\": \"user\"}"),
                                    @ExampleObject(name = "Master Login", value = "{\"username\": \"master\", \"password\": \"master\"}")
                    }))
            @RequestBody @Validated SignInRequest request) {
        return authenticationService.signIn(request);
        //TODO сделать обработку ошибок (неверный юзернейм или пароль)
    }

    @PostMapping("integration/sign-up")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Генерация токена для интеграции, доступно только администратору")
    public TokenAuthenticationResponse generateToken(@RequestBody TokenGenerationRequest tokenGenerationRequest){
        return authenticationService.generateIntegrationToken(tokenGenerationRequest);
    }

    @GetMapping("integration/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получение ролей для интеграции")
    public Set<RoleResponse> getIntegrationRoles(){
        return authenticationService.findIntegrationRoles();
    }

    @GetMapping("roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получение всех ролей, доступно только администратору")
    public Set<RoleResponse> getRoles(){
        return authenticationService.findRoles();
    }
}
