package com.bsoft.adres.service;

import com.bsoft.adres.database.ApiKeyDTO;
import com.bsoft.adres.repositories.ApiKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ApiKeyService {

    private static final String API_KEY_PATTERN = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})$"; // 32 alphanumeric characters

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public boolean isValidApiKey(String apiKey) {
        boolean result = true;

        // 1. Validate API Key Pattern
        if (!apiKey.matches(API_KEY_PATTERN)) {
            return false;
        }

        // 2. Check if API Key exists in the database
        Optional<ApiKeyDTO> apiKeyOptional = apiKeyRepository.findByKey(apiKey);
        result = apiKeyOptional.isPresent();

        log.trace("isValidApiKey checking key: {} value: {}", apiKey, result);
        return result;
    }

    public ApiKeyDTO generateApiKey() {
        String randomKey = UUID.randomUUID().toString();
        ApiKeyDTO apiKey = new ApiKeyDTO();
        apiKey.setApikey(randomKey);

        return apiKey;
    }
}
