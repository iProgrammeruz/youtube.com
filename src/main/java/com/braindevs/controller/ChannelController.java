package com.braindevs.controller;

import com.braindevs.dto.channel.ChannelCreateDto;
import com.braindevs.dto.channel.ChannelDto;
import com.braindevs.dto.channel.ChannelUpdateDto;
import com.braindevs.enums.Status;
import com.braindevs.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/v1/create")
    public ResponseEntity<ChannelDto> create(@Valid @RequestBody ChannelCreateDto dto) {
        ChannelDto response = channelService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/v1/update/{chanelId}")
    public ResponseEntity<ChannelDto> update(@PathVariable String chanelId,
                                             @Valid @RequestBody ChannelUpdateDto dto) {
        ChannelDto response = channelService.update(chanelId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/v1/update/photo/{chanelId}")
    public ResponseEntity<String> updatePhoto(@PathVariable String chanelId,
                                              @RequestParam String photoId) {
        channelService.updatePhoto(chanelId, photoId);
        return ResponseEntity.status(HttpStatus.OK).body("channel photo successfully updated");
    }

    @PutMapping("/v1/update/banner/{chanelId}")
    public ResponseEntity<String> updateBanner(@PathVariable String chanelId,
                                               @RequestParam String bannerId) {
        channelService.updateBanner(chanelId, bannerId);
        return ResponseEntity.status(HttpStatus.OK).body("channel banner successfully updated");
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ChannelDto>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                   @RequestParam(defaultValue = "5") int pageSize) {
        Page<ChannelDto> response = channelService.getAll(pageNumber-1,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/v2/byId/{chanelId}")
    public ResponseEntity<ChannelDto> getChanelById(@PathVariable String chanelId) {
       ChannelDto response = channelService.getChanelById(chanelId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/v2/change/status/{chanelId}")
    public ResponseEntity<String> changeStatus(@PathVariable String chanelId,
                                               @RequestParam Status status) {
        channelService.changeStatus(chanelId, status);
        return ResponseEntity.status(HttpStatus.OK).body("channel status successfully changed");
    }

    @GetMapping("/v1/user/chanels")
    public ResponseEntity<List<ChannelDto>> getUserChanels() {
       List<ChannelDto> response = channelService.getUserChanels();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
