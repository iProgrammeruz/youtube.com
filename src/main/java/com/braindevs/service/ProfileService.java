package com.braindevs.service;

import com.braindevs.dto.JwtDto;
import com.braindevs.dto.profile.ProfileDto;
import com.braindevs.dto.profile.ProfileUpdateDto;
import com.braindevs.entity.ProfileEntity;
import com.braindevs.enums.ProfileStatus;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.ProfileRepository;
import com.braindevs.util.JwtUtil;
import com.braindevs.util.MD5Util;
import com.braindevs.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final EmailSenderService emailSenderService;
    private final EmailHistoryService emailHistoryService;

    public String changePassword(String oldPassword, String newPassword) {
        ProfileEntity profile = SecurityUtil.getProfile();
        if (!profile.getPassword().equals(MD5Util.getMd5(oldPassword))) {
            throw new AppBadException("wrong password");
        }
        profile.setPassword(MD5Util.getMd5(newPassword));
        profileRepository.save(profile);
        return "password changed";
    }

    public String changeEmail(String newEmail) {
        ProfileEntity profile = SecurityUtil.getProfile();

        if (profile.getStatus().equals(ProfileStatus.BLOCK)) {
            throw new AppBadException("profile status is block");
        }
        if (profile.getEmail().equals(newEmail) || profileRepository.existsByEmail(newEmail)) {
            throw new AppBadException("email already in use");
        }

        String token = JwtUtil.generateToken(profile.getId(), newEmail, profile.getRole());
        emailSenderService.sendEmailForChange(token);
        return "please verify your new email.";
    }

    public String verifyEmail(String token) {
        JwtDto dto = JwtUtil.decode(token);
        String email = dto.getUsername();
        Integer profileId = dto.getId();

        emailHistoryService.isNotExpiredEmail(email);

        profileRepository.updateEmail(profileId, email);
        return "email changed successfully";
    }

    public ProfileDto update(ProfileUpdateDto dto) {
        ProfileEntity profile = SecurityUtil.getProfile();

        if (profile.getStatus().equals(ProfileStatus.BLOCK)) {
            throw new AppBadException("profile status is block");
        }

        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        ProfileEntity saved = profileRepository.save(profile);
        return toDto(saved);
    }

    private ProfileDto toDto(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public ProfileEntity getProfile(String email) {
        return profileRepository.findByEmailAndVisibleTrue(email)
                .orElseThrow(() -> new AppBadException("profile not found"));
    }

    public ProfileDto getProfileDetail() {
        ProfileEntity profile = SecurityUtil.getProfile();
        return toDto(profile);
    }
}
