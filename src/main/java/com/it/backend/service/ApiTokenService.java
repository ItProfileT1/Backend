package com.it.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiTokenService {

    private final ApiClientService apiClientService;

    public boolean isTokenValid(String token){
        return apiClientService.existsByToken(token);
    }
}
