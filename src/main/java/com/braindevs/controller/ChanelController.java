package com.braindevs.controller;

import com.braindevs.dto.chanel.ChanelCreateDto;
import com.braindevs.dto.chanel.ChanelDto;
import com.braindevs.dto.chanel.ChanelUpdateDto;
import com.braindevs.enums.Status;
import com.braindevs.service.ChanelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chanel")
@RequiredArgsConstructor
public class ChanelController {
    private final ChanelService chanelService;

    @PostMapping("/v1/create")
    public ResponseEntity<ChanelDto> create(@Valid @RequestBody ChanelCreateDto dto) {
        ChanelDto response = chanelService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/v1/update/{chanelId}")
    public ResponseEntity<ChanelDto> update(@PathVariable String chanelId,
                                            @Valid @RequestBody ChanelUpdateDto dto) {
        ChanelDto response = chanelService.update(chanelId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/v1/update/photo/{chanelId}")
    public ResponseEntity<String> updatePhoto(@PathVariable String chanelId,
                                              @RequestParam String photoId) {
        chanelService.updatePhoto(chanelId, photoId);
        return ResponseEntity.status(HttpStatus.OK).body("chanel photo successfully updated");
    }

    @PutMapping("/v1/update/banner/{chanelId}")
    public ResponseEntity<String> updateBanner(@PathVariable String chanelId,
                                               @RequestParam String bannerId) {
        chanelService.updateBanner(chanelId, bannerId);
        return ResponseEntity.status(HttpStatus.OK).body("chanel banner successfully updated");
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ChanelDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize) {
        Page<ChanelDto> response = chanelService.getAll(pageNumber-1,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/v2/byId/{chanelId}")
    public ResponseEntity<ChanelDto> getChanelById(@PathVariable String chanelId) {
       ChanelDto response = chanelService.getChanelById(chanelId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/v2/change/status/{chanelId}")
    public ResponseEntity<String> changeStatus(@PathVariable String chanelId,
                                               @RequestParam Status status) {
        chanelService.changeStatus(chanelId, status);
        return ResponseEntity.status(HttpStatus.OK).body("chanel status successfully changed");
    }

    @GetMapping("/v1/user/chanels")
    public ResponseEntity<List<ChanelDto>> getUserChanels() {
       List<ChanelDto> response = chanelService.getUserChanels();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
