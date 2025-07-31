package com.example.auth_service.controller;

import com.example.auth_service.dto.JwtRequest;
import com.example.auth_service.dto.JwtResponse;
import com.example.auth_service.dto.UserDto;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.service.JwtService;
import com.example.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/token")
    public final ResponseEntity<JwtResponse> createAccessToken(@RequestBody JwtRequest request) throws Exception {
        return ResponseEntity.ok(jwtService.createAccessToken(request));
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        UserDto created = userMapper.toDto(userService.createUser(userDto));
        return ResponseEntity.ok().body(created);
    }
}
