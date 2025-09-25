package ru.kashtanov.validation_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.validation_service.dto.UserRequest;
import ru.kashtanov.validation_service.dto.ValidationResponseDto;
import ru.kashtanov.validation_service.service.ValidationService;
import ru.kashtanov.validation_service.util.EnvConfig;


@RestController
@RequestMapping("api/v1/validator")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @PostMapping("/validate-session")
    public ResponseEntity<ValidationResponseDto> validateUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt = "";
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7).trim();
        }
        boolean isValid = validationService.validateSessionBelongsToUser(jwt, Long.parseLong(userRequest.getUserId()));

        if (isValid) {
            System.out.println("Validated session");
            return ResponseEntity.ok(new ValidationResponseDto(true,userRequest.getUserId() + " is valid"));
        }
        System.out.println("Not validated session");
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).
                body(new ValidationResponseDto(false,userRequest.getUserId() + " is not valid"));

    }


    @GetMapping("/check")
    public String getCheckAPI() {
        return "Check, it works";
    }


}
