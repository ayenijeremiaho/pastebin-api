package com.ayenijeremiaho.pastebinapi.auth.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
