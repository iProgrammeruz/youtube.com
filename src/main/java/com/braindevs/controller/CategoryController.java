package com.braindevs.controller;

import com.braindevs.dto.category.CategoryDto;
import com.braindevs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/admin/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto category) {
        return  ResponseEntity.ok().body(categoryService.create(category));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,@RequestBody CategoryDto dto){
        return  ResponseEntity.ok().body(categoryService.update(id,dto));

    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(categoryService.delete(id));
    }

    @GetMapping("/getCategoryList")
    public ResponseEntity<List<CategoryDto>> getList(){
        return ResponseEntity.ok().body(categoryService.getList());
    }




}
