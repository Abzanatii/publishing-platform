package com.abz.pub.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abz.pub.domain.dtos.CreateTagsRequest;
import com.abz.pub.domain.dtos.TagResponse;
import com.abz.pub.domain.entities.Tag;
import com.abz.pub.mappers.TagMapper;
import com.abz.pub.services.TagService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping (path = "/tags")
@RequiredArgsConstructor 
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping 
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<Tag> tags = tagService.getTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagReponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping 
    public ResponseEntity<List<TagResponse>> createTags (@RequestBody CreateTagsRequest createTagsRequest){
        List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
        List<TagResponse> createdTagResponses = savedTags.stream().map(tagMapper::toTagReponse).toList();
        return new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED);
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
