package com.braindevs.controller;

import com.braindevs.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        String response = profileService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change/email")
    public ResponseEntity<String> changeEmail(@RequestParam String newEmail) {
        String response = profileService.changeEmail(newEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        String response = profileService.verifyEmail(token);
        return ResponseEntity.ok(response);
    }
}