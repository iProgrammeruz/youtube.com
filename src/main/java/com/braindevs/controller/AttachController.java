package com.braindevs.controller;

import com.braindevs.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {

    private final AttachService attachService;


    @PostMapping("/upload1")
    public ResponseEntity<String> upload1(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }


}
