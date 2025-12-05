package com.example.p3.service;

import com.example.p3.repositories.TagRepository;
import com.example.p3.entities.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        // findAll is a part of JpaRepository
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(int id) {
        return tagRepository.findById(id);
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
