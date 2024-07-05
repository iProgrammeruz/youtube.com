package com.braindevs.controller;

import com.braindevs.dto.video.VideoCreateDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;


    @PostMapping("/create")
    public ResponseEntity<VideoDto> create(@Valid @RequestBody VideoCreateDto videoCreateDto) {
        VideoDto response = videoService.create(videoCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }










    @GetMapping("/shareCountIncrease/{videoId}")
    void increaseShareCount(@PathVariable String videoId) {
        videoService.increaseShareCount(videoId);
    }


}
