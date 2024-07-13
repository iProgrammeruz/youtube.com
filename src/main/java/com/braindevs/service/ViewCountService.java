package com.braindevs.service;

import com.braindevs.entity.ViewCountEntity;
import com.braindevs.repository.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ViewCountService {

    private final ViewCountRepository viewCountRepository;

    public void create(String videoId, Integer count, Long profileId) {
        ViewCountEntity entity = new ViewCountEntity();
        entity.setVideoId(videoId);
        entity.setProfileId(profileId);
        entity.setCount(count);
        viewCountRepository.save(entity);
    }
}
