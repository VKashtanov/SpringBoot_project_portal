package ru.kashtanov.validation_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ValidationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;



    public ValidationService(RestTemplate restTemplate, ObjectMapper objectMapper1) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper1;
    }

    public boolean validateSessionBelongsToUser(String jsessionId, long expectedUserId) {
        try {
            String url = "http://localhost:8080/o/headless-admin-user/v1.0/my-user-account";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "JSESSIONID=" + jsessionId);
            headers.add("Origin", "http://localhost:8080"); // ← REQUIRED to avoid 403

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return false;
            }

            JsonNode userNode = objectMapper.readTree(response.getBody());
            long actualUserId = userNode.get("id").asLong();

            return actualUserId == expectedUserId; // ← Secure: session user must match requested user

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}