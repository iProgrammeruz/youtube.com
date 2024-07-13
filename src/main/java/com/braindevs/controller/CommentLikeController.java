package com.braindevs.controller;

import com.braindevs.dto.comment.CommentLikeCreateDto;
import com.braindevs.dto.comment.CommentLikeDto;
import com.braindevs.service.CommentLikeService;
import com.braindevs.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment_like")
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeservice;

    @PostMapping("/like&dislike")
    public ResponseEntity reaction(@Valid @RequestBody CommentLikeCreateDto dto) {
        commentLikeservice.reaction(dto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getByCurrentUserId")
    public ResponseEntity<Page<CommentLikeDto>> getByCurrentUserId(@RequestParam(defaultValue = "1") int pageNumber,
                                                                   @RequestParam(defaultValue = "5") int pageSize) {
        Page<CommentLikeDto> response = commentLikeservice.getByUserId(SecurityUtil.getProfileId(), pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<Page<CommentLikeDto>> getByUserId(@PathVariable long userId,
                                                            @RequestParam(defaultValue = "1") int pageNumber,
                                                            @RequestParam(defaultValue = "5") int pageSize) {
        Page<CommentLikeDto> response = commentLikeservice.getByUserId(userId, pageNumber - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
