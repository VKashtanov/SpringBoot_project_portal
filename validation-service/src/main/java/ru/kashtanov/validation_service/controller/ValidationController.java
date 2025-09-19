package ru.kashtanov.validation_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.validation_service.dto.UserRequest;

@RestController
@RequestMapping("api/v1/validator")
public class ValidationController {

    @PostMapping("/validate-session")
    public void validateUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        System.out.println("userId: " + userRequest.getUserId());
        System.out.println();
        System.out.println(request.getSession().getId());

    }


    @GetMapping("/check")
    public String getCheckAPI() {
        return "Check, it works";
    }


}
