package com.it.backend.service.security;

import com.it.backend.service.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiTokenService {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private final ApiClientService apiClientService;

    public boolean isTokenValid(String token) {
        return apiClientService.existsByToken(token);
    }

    public String generateToken() {
        byte[] randomBytes = new byte[48]; // 48 байт -> 64 символа в Base64
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
