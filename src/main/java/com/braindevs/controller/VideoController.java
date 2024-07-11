package com.braindevs.controller;

import com.braindevs.dto.video.VideoCreateDto;
import com.braindevs.dto.video.VideoDto;
import com.braindevs.dto.video.VideoShortInfoDto;
import com.braindevs.dto.video.VideoUpdateDto;
import com.braindevs.enums.VideoStatus;
import com.braindevs.service.VideoService;
import com.braindevs.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> create(@Valid @RequestBody VideoCreateDto videoCreateDto) {
        String response = videoService.create(videoCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("update/detail/{videoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
    public ResponseEntity<String> update(@PathVariable String videoId,
                                         @Valid @RequestBody VideoUpdateDto videoUpdateDto) {
        String response = videoService.update(videoUpdateDto, videoId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("update/status/{videoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
    public ResponseEntity<String> updateStatus(@PathVariable String videoId,
                                               @Valid @RequestParam VideoStatus videoStatus) {
        String response = videoService.updateStatus(videoId, videoStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    //Not authorized user
    @GetMapping("/viewCountIncrease/{videoId}")
    void increaseViewCount(@PathVariable String videoId) {
        videoService.increaseViewCount(videoId, null);
    }


    //Authorized user
    @GetMapping("/viewCountIncreaseRegister/{videoId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    void increaseViewCountWithUser(@PathVariable String videoId) {
        videoService.increaseViewCount(videoId, SecurityUtil.getProfileId());
    }


    @GetMapping("/shareCountIncrease/{videoId}")
    void increaseShareCount(@PathVariable String videoId) {
        videoService.increaseShareCount(videoId);
    }


    @GetMapping("/byCategoryId/{categoryId}")
    public ResponseEntity<PageImpl<VideoShortInfoDto>> getVideosByCategoryId(@PathVariable Integer categoryId,
                                                                             @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                                                             @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        PageImpl<VideoShortInfoDto> response = videoService.getVideosByCategoryId(categoryId, pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/byTitle")
    public ResponseEntity<PageImpl<VideoShortInfoDto>> getVideosByTitle(@RequestParam String title,
                                                                        @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                                                        @RequestParam(value = "size", defaultValue = "5") int pageSize) {
        PageImpl<VideoShortInfoDto> response = videoService.getVideosByTitle(title, pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


//    @GetMapping("/byTagId{tagId}")
//    public ResponseEntity<PageImpl<VideoShortInfoDto>> getVideosByTagId(@PathVariable String tagId,
//                                                                        @RequestParam(value = "page", defaultValue = "1") int pageNumber,
//                                                                        @RequestParam(value = "size", defaultValue = "5") int pageSize) {
//        PageImpl<VideoShortInfoDto> response = videoService.getVideosByTagId(tagId, pageNumber - 1, pageSize);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
