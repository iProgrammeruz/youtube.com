package com.braindevs.controller;

import com.braindevs.dto.channnel.ChannelCreateDto;
import com.braindevs.dto.channnel.ChannelDto;
import com.braindevs.dto.channnel.ChannelUpdateDto;
import com.braindevs.entity.ChannelEntity;
import com.braindevs.enums.Status;
import com.braindevs.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelDto> create(@RequestBody ChannelCreateDto dto){
        return ResponseEntity.ok().body(channelService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChannelDto> update(@RequestBody ChannelUpdateDto dto , @PathVariable String  channelId){
        return ResponseEntity.ok().body(channelService.update(dto,channelId));
    }

    @PutMapping("/updatePhoto/{channelId}")
    public ResponseEntity<ChannelDto> updatePhoto(@PathVariable String channelId ,
                                                  @RequestParam String photoId ){
        return ResponseEntity.ok().body(channelService.updatePhoto(channelId,photoId));

    }

    @PutMapping("/updateBanner/{channelId}")
    public ResponseEntity<ChannelDto> updateBanner(@PathVariable String channelId,
                                                   @RequestParam String bannerId ){
        return ResponseEntity.ok().body(channelService.updateBanner(channelId,bannerId));
    }

    @GetMapping("/admin/getChannels")
    public ResponseEntity<Page<ChannelDto>> getChannels(@RequestParam("page") Integer page ,
                                                        @RequestParam("size") Integer size){
        return ResponseEntity.status(HttpStatus.OK).body(channelService.getChannels(page - 1 , size));

    }

    @GetMapping("/admin/getById/{channelId}")
    public ResponseEntity<ChannelDto> getById(@PathVariable String channelId){
        return ResponseEntity.ok().body(channelService.getById(channelId));
    }

    @PutMapping("/changeStatus/{channelId}")
    public ResponseEntity<String > changeStatus(@PathVariable String channelId, Status status){
        channelService.changeStatus(channelId,status);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/getByUserChannelList")
    public ResponseEntity<Page<ChannelDto>> getByUserChannelList(@RequestParam("page") Integer page ,
                                                                 @RequestParam("size") Integer size){

        return ResponseEntity.status(HttpStatus.OK).body(channelService.getByUserChannelList(page - 1 , size));

    }














}
