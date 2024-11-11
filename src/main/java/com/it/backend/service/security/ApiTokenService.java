package com.it.backend.service.security;

import com.it.backend.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiTokenService {

    private final ApiClientService apiClientService;

    public boolean isTokenValid(String token){
        return apiClientService.existsByToken(token);
    }
}
