package com.braindevs.controller;

import com.braindevs.dto.attach.AttachDto;
import com.braindevs.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping("/upload2")
    public ResponseEntity<AttachDto> upload2(@RequestParam("file") MultipartFile file) {
        AttachDto response = attachService.saveAttach(file);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(value = "/open1/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open1(@PathVariable("fileName") String fileName) {
        return this.attachService.loadImage(fileName);
    }


    @GetMapping(value = "/open2/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open2(@PathVariable("fileName") String fileName) {
        return this.attachService.load(fileName);
    }


    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] openGeneral(@PathVariable("fileName") String fileName) {
        return attachService.openGeneral(fileName);
    }


    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);

    }

    @DeleteMapping("/delete/{fineName}")
    public ResponseEntity<String> delete(@PathVariable("fineName") String fileName) {
         attachService.delete(fileName);
         return ResponseEntity.ok("Deleted " + fileName);
    }
}
