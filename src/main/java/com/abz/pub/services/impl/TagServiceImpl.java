package com.abz.pub.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abz.pub.domain.entities.Tag;
import com.abz.pub.repos.TagRepo;
import com.abz.pub.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class TagServiceImpl implements TagService{

    private final TagRepo tagRepo;
    
    @Override
    public List<Tag> getTags() {
        return tagRepo.findAllWithPostCount();
    }

    @Transactional 
    @Override
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepo.findByNameIn(tagNames);
        
        Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());
        
        List<Tag> newTags = tagNames.stream().filter(name -> !existingTagNames.contains(name))
        .map(name -> Tag.builder()
            .name(name)
            .posts(new HashSet<>())
            .build())
            .collect(Collectors.toList());
        
        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = tagRepo.saveAll(newTags);
        }

        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Transactional 
    @Override
    public void deleteTag(UUID id) {
        tagRepo.findById(id).ifPresent(tag -> {
            if (!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Tag has posts cannot delete.");
            }
            tagRepo.deleteById(id);
        });
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found."));
    }

    @Override
    public List<Tag> getTagByIds(Set<UUID> ids) {
        List<Tag> foundTags = tagRepo.findAllById(ids);
        if (foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("Not all tags exists.");
        }
        return foundTags;
    }

}
