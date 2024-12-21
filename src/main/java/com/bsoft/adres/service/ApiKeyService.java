package com.bsoft.adres.service;

import com.bsoft.adres.database.ApiKeyDao;
import com.bsoft.adres.repositories.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    private static final String API_KEY_PATTERN = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})$"; // 32 alphanumeric characters

    public boolean isValidApiKey(String apiKey) {
        // 1. Validate API Key Pattern
        if (!apiKey.matches(API_KEY_PATTERN)) {
            return false;
        }

        // 2. Check if API Key exists in the database
        Optional<ApiKeyDao> apiKeyOptional = apiKeyRepository.findByKey(apiKey);
        return apiKeyOptional.isPresent();
    }
}
