package com.example.demo.controller;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.SignRequest;
import com.example.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(authenticationService.login(request),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SignRequest request) throws Exception {
        authenticationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
