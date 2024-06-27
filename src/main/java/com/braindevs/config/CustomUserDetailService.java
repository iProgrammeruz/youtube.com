package com.braindevs.config;

import com.braindevs.entity.ProfileEntity;
import com.braindevs.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepository.findByEmailAndVisibleTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetail(profile);
    }
}

