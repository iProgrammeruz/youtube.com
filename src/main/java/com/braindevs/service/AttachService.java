package com.braindevs.service;

import com.braindevs.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachService {

    private final AttachRepository attachRepository;



}
