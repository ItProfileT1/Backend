package com.it.backend.service;

import com.it.backend.dto.request.TokenGenerationRequest;
import com.it.backend.entity.ApiClient;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiClientService {

    private final ApiClientRepository apiClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final IntegrationRoleService integrationRoleService;

    public Iterable<ApiClient> findAll(){
        return apiClientRepository.findAll();
    }

    public boolean existsByToken(String token){
        for (ApiClient apiClient : findAll()) {
            if (passwordEncoder.matches(token, apiClient.getToken())){
                return true;
            }
        }
        return false;
    }

    public ApiClient getByToken(String token){
        for (ApiClient apiClient : findAll()) {
            if (passwordEncoder.matches(token, apiClient.getToken())){
                return apiClient;
            }
        }
        throw new EntityNotFoundException(String.format("apiClient.with.token.%s.not.found", token), 0L);
    }

    public UserDetailsService userDetailsService() {
        return this::getByToken;
    }

    public void create(String token, TokenGenerationRequest request) {
        ApiClient apiClient = ApiClient.builder()
                .token(passwordEncoder.encode(token))
                .integrationRole(integrationRoleService.findById(request.integrationRoleId()))
                .build();
        if (existsByToken(token)){
            throw new RuntimeException("Already exists");
            //TODO ошибка Уже существует
        }
        apiClientRepository.save(apiClient);
    }
}
