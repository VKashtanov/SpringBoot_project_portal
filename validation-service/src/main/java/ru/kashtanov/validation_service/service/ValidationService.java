package ru.kashtanov.validation_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kashtanov.validation_service.util.EnvConfig;

import java.util.Map;


@Service
public class ValidationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String CLIENT_ID = EnvConfig.get("CLIENT_ID");
    private static final String CLIENT_SECRET = EnvConfig.get("DATABASE_URL");


    public ValidationService(RestTemplate restTemplate, ObjectMapper objectMapper1) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper1;
    }

    public boolean validateSessionBelongsToUser(String token, long expectedUserId) {
        try {
            String accessToken = getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth("Bearer "+ accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                    "http://localhost:8080/o/headless-admin-user/v1.0/user-accounts/" + expectedUserId,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return true;
        } catch (HttpClientErrorException e) {
            return false; // User not found
        } catch (Exception e) {
            return false; // Network error etc.
        }

    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8080/o/oauth2/token",
                request,
                Map.class
        );
        return (String) response.getBody().get("access_token");
    }
}