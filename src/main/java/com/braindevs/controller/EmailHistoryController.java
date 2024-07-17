package com.braindevs.controller;

import com.braindevs.dto.email.EmailHistoryDto;
import com.braindevs.dto.email.EmailHistoryFilterDto;
import com.braindevs.service.EmailHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emailHistory")
@RequiredArgsConstructor
public class EmailHistoryController {
    private final EmailHistoryService emailHistoryService;


    @GetMapping("/admin/pagination")
    public ResponseEntity<Page<EmailHistoryDto>> pagination(@RequestParam (defaultValue = "1") Integer pageNumber,
                                                            @RequestParam (defaultValue = "5") Integer pageSize) {
        Page<EmailHistoryDto> page = emailHistoryService.pagination(pageNumber - 1, pageSize);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/byEmail")
    public ResponseEntity<Page<EmailHistoryDto>> getAllByEmail(@RequestParam (defaultValue = "1") Integer pageNumber,
                                                               @RequestParam (defaultValue = "5") Integer pageSize,
                                                               @RequestParam String email) {
        Page<EmailHistoryDto> page = emailHistoryService.getAllByEmail(pageNumber - 1, pageSize, email);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<EmailHistoryDto>> filter(@RequestParam Integer pageNumber,
                                                        @RequestParam Integer pageSize,
                                                        @RequestBody EmailHistoryFilterDto dto) {
        Page<EmailHistoryDto> page = emailHistoryService.filter(dto, pageNumber - 1, pageSize);
        return ResponseEntity.ok(page);
    }
}
