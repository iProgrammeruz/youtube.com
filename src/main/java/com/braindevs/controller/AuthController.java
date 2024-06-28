package com.braindevs.controller;

import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.profile.ProfileLoginDto;
import com.braindevs.dto.profile.ProfileRegistrationDto;
import com.braindevs.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registr")
    public ResponseEntity<String> registration(@Valid @RequestBody ProfileRegistrationDto dto) {
        String response = authService.registration(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable String token) {
        String response = authService.verification(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@Valid @RequestBody ProfileLoginDto dto) {
        ProfileDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}
