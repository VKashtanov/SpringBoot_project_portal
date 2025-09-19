package ru.kashtanov.validation_service.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userId;

    public UserRequest() {
    }
    public UserRequest(String userId) {
        this.userId = userId;
    }
}
