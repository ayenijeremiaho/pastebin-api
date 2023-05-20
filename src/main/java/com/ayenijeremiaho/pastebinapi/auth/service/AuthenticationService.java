package com.ayenijeremiaho.pastebinapi.auth.service;

import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationRequest;
import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse getAuthenticationResponse(AuthenticationRequest request);
}
