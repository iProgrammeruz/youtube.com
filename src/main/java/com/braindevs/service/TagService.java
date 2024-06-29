package com.braindevs.service;

import com.braindevs.dto.category.CategoryDto;
import com.braindevs.dto.tag.TagDto;
import com.braindevs.entity.TagEntity;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;


    public TagDto create(TagDto dto) {
        Optional<TagEntity> exists = tagRepository.findByName(dto.getName());
        if (exists.isPresent()) {
            throw new AppBadException("Tag already exists");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        tagRepository.save(entity);
        TagDto tagDto = new TagDto();
        tagDto.setName(entity.getName());
        tagDto.setId(entity.getId());
        tagDto.setCreatedDate(entity.getCreatedDate());
        return tagDto;
    }



    public TagEntity findById(Integer id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Tag not found"));

    }

    public Boolean update(Integer id, TagDto dto) {
        TagEntity tagEntity = findById(id);
        tagEntity.setName(dto.getName());
        tagRepository.save(tagEntity);
        return true;
    }

    public Boolean delete(Integer id) {
        TagEntity tagEntity = findById(id);
        tagRepository.delete(tagEntity);
        return true;
    }

    public List<TagDto> getList() {
        Iterable<TagEntity> all = tagRepository.findAll();
        List<TagDto> list = new LinkedList<>();
        for (TagEntity tagEntity : all) {
            TagDto tagDto = new TagDto();
            tagDto.setId(tagEntity.getId());
            tagDto.setName(tagEntity.getName());
            tagDto.setCreatedDate(tagEntity.getCreatedDate());
            list.add(tagDto);
        }
        return list;
    }
}
