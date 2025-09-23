package ru.kashtanov.validation_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.validation_service.dto.UserRequest;
import ru.kashtanov.validation_service.service.ValidationService;

@RestController
@RequestMapping("api/v1/validator")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @PostMapping("/validate-session")
    public void validateUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        boolean isValid = validationService.validateSessionBelongsToUser(request.getSession().getId(), Long.parseLong(userRequest.getUserId()));
        System.out.println();
        System.out.println("Validated session: " + isValid);
        System.out.println();
        System.out.println("userId: " + userRequest.getUserId());
        System.out.println();
        System.out.println(request.getSession().getId());

    }


    @GetMapping("/check")
    public String getCheckAPI() {
        return "Check, it works";
    }


}
