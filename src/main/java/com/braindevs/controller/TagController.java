package com.braindevs.controller;

import com.braindevs.dto.category.CategoryDto;
import com.braindevs.dto.tag.TagDto;
import com.braindevs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;


    @PostMapping("/create")
    public ResponseEntity<TagDto> create(@RequestBody TagDto dto) {
        return  ResponseEntity.ok().body(tagService.create(dto));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,@RequestBody TagDto dto){
        return  ResponseEntity.ok().body(tagService.update(id,dto));

    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tagService.delete(id));
    }

    @GetMapping("/getTagList")
    public ResponseEntity<List<TagDto>> getList(){
        return ResponseEntity.ok().body(tagService.getList());
    }

}
