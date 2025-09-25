package ru.kashtanov.validation_service.dto;

import lombok.Data;


public record ValidationResponseDto(boolean isValid, String message) {}
