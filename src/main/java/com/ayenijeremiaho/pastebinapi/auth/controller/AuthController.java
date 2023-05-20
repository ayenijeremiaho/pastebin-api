package com.ayenijeremiaho.pastebinapi.auth.controller;

import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationRequest;
import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationResponse;
import com.ayenijeremiaho.pastebinapi.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse response = authenticationService.getAuthenticationResponse(authenticationRequest);
        return ResponseEntity.ok(response);
    }
}