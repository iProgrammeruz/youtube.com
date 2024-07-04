package com.braindevs.controller;

import com.braindevs.config.ResourseBundleConfig;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.profile.ProfileLoginDto;
import com.braindevs.dto.profile.ProfileRegistrationDto;
import com.braindevs.enums.LanguageEnum;
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
    private final ResourseBundleConfig resourseBundleConfig;

    @PostMapping("/registr")
    public ResponseEntity<String> registration(@Valid @RequestBody ProfileRegistrationDto dto,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum lang) {
        String response = authService.registration(dto, lang);
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
