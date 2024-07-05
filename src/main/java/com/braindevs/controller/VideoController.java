package com.braindevs.controller;

import com.braindevs.dto.video.VideoCreateDto;
import com.braindevs.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;


    @PostMapping("/create")
    public ResponseEntity<String> createVideo(@Valid @RequestBody VideoCreateDto videoCreateDto) {
        String response = videoService.create(videoCreateDto);
        return ResponseEntity.ok(response);
    }








    @GetMapping("/shareCountIncrease/{videoId}")
    void increaseShareCount(@PathVariable String videoId) {
        videoService.increaseShareCount(videoId);
    }


}
