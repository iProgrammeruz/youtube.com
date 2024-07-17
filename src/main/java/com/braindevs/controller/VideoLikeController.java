package com.braindevs.controller;

import com.braindevs.dto.video.VideoLikeCreateDto;
import com.braindevs.dto.video.VideoLikeDto;
import com.braindevs.enums.EmotionStatus;
import com.braindevs.service.VideoLikeService;
import com.braindevs.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video_like")
@RequiredArgsConstructor
public class VideoLikeController {
    private final VideoLikeService videoLikeService;

    @PostMapping("/like&dislike")
    public ResponseEntity reaction(@Valid @RequestBody VideoLikeCreateDto dto) {
        videoLikeService.reaction(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getByCurrentUserId")
    public ResponseEntity<Page<VideoLikeDto>> getByCurrentUserId(@RequestParam EmotionStatus reaction,
                                                                 @RequestParam(defaultValue = "1") int pageNumber,
                                                                 @RequestParam(defaultValue = "5") int pageSize) {
        Page<VideoLikeDto> response = videoLikeService.getByUserId(SecurityUtil.getProfileId(), reaction, pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<Page<VideoLikeDto>> getByUserId(@PathVariable long userId,
                                                          @RequestParam EmotionStatus reaction,
                                                          @RequestParam(defaultValue = "1") int pageNumber,
                                                          @RequestParam(defaultValue = "5") int pageSize) {
        Page<VideoLikeDto> response = videoLikeService.getByUserId(userId, reaction, pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
