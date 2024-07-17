package com.braindevs.controller;

import com.braindevs.dto.report.ReportCreateDto;
import com.braindevs.dto.report.ReportDto;
import com.braindevs.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<String> createReport(@RequestBody ReportCreateDto dto) {
        return ResponseEntity.ok(reportService.createReport(dto));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ReportDto>> pagination(@RequestParam(name = "page",defaultValue = "1") int page,
                                                          @RequestParam(name = "size",defaultValue = "3") int size) {
        PageImpl<ReportDto> responseDTOList=reportService.pagination(page-1,size);
        return ResponseEntity.ok(responseDTOList);
    }

    @DeleteMapping("/removeById/{id}")
    public ResponseEntity<String> getById(String id) {
        reportService.delete(id);
        return ResponseEntity.ok("report removed");
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<List<ReportDto>> getByUserId(Long userid) {
        List<ReportDto> responseDTOList=reportService.getByUserId(userid);
        return ResponseEntity.ok(responseDTOList);
    }
}
