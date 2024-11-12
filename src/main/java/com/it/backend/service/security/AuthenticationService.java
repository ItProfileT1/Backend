package com.it.backend.service.security;

import com.it.backend.dto.request.TokenGenerationRequest;
import com.it.backend.dto.response.JwtAuthenticationResponse;
import com.it.backend.dto.request.SignInRequest;
import com.it.backend.dto.request.SignUpRequest;
import com.it.backend.dto.response.RoleResponse;
import com.it.backend.dto.response.TokenAuthenticationResponse;
import com.it.backend.dto.response.UserResponse;
import com.it.backend.entity.User;
import com.it.backend.mapper.IntegrationRoleMapper;
import com.it.backend.mapper.UserMapper;
import com.it.backend.service.ApiClientService;
import com.it.backend.service.IntegrationRoleService;
import com.it.backend.service.RoleService;
import com.it.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final ApiClientService apiClientService;
    private final ApiTokenService apiTokenService;
    private final IntegrationRoleService integrationRoleService;
    private final IntegrationRoleMapper integrationRoleMapper;

    public UserResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(roleService.findById(request.roleId()))
                .build();
        userService.create(user);
        return UserMapper.INSTANCE.toUserResponse(user, request.password());
    }

    public JwtAuthenticationResponse signIn(SignInRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.username());
        var jwt = jwtService.generateToken(user);
        var roleResponse = roleService.roleToRoleResponse(userService
                .getByUsername(request.username())
                .getRole());
        return new JwtAuthenticationResponse(jwt, roleResponse);
    }

    public Set<RoleResponse> findRoles() {
        return roleService.findAll();
    }

    public TokenAuthenticationResponse generateIntegrationToken(TokenGenerationRequest request) {
        var token = apiTokenService.generateToken();
        apiClientService.create(token, request);
        return new TokenAuthenticationResponse(token);
    }

    public Set<RoleResponse> findIntegrationRoles() {
        return integrationRoleMapper.toRoleResponses(integrationRoleService.findAll());
    }

    public Set<UserResponse> findAllUsers() {
        Set<UserResponse> userResponses = new HashSet<>();
        for (User user : userService.findAll()) {
            userResponses.add(UserMapper.INSTANCE.toUserResponse(user,null));
        }
        return userResponses;
    }
}
