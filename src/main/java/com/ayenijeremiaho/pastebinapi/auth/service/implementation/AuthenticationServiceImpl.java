package com.ayenijeremiaho.pastebinapi.auth.service.implementation;

import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationRequest;
import com.ayenijeremiaho.pastebinapi.auth.dto.AuthenticationResponse;
import com.ayenijeremiaho.pastebinapi.auth.service.AuthenticationService;
import com.ayenijeremiaho.pastebinapi.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse getAuthenticationResponse(AuthenticationRequest authenticationRequest) {
        Authentication authentication = getAuthentication(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);
        return AuthenticationResponse.builder().accessToken(jwt).build();
    }

    private Authentication getAuthentication(AuthenticationRequest authenticationRequest) {

        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
