package com.example.auth_service.service;

import com.example.auth_service.config.JwtConfig;
import com.example.auth_service.dto.JwtRequest;
import com.example.auth_service.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig config;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse createAccessToken(JwtRequest request) throws Exception {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        return new JwtResponse(config.generateToken(userDetails));
    }
}
