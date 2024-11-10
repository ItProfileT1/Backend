package com.it.backend.service;

import com.it.backend.entity.ApiClient;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiClientService {

    private final ApiClientRepository apiClientRepository;

    public Iterable<ApiClient> findAll(){
        return apiClientRepository.findAll();
    }

    public boolean existsByToken(String token){
        return apiClientRepository.existsByToken(token);
    }

    public ApiClient getByToken(String token){
        for (ApiClient apiClient : findAll()) {
            if (token.equals(apiClient.getToken())){
                return apiClient;
            }
        }
        throw new EntityNotFoundException(String.format("apiClient.with.token.%s.not.found", token), 0L);
    }

    public UserDetailsService userDetailsService() {
        return this::getByToken;
    }

}
