package com.abz.pub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.abz.pub.domain.CreatePostRequest;
import com.abz.pub.domain.UpdatePostRequest;
import com.abz.pub.domain.dtos.CreatePostRequestDto;
import com.abz.pub.domain.dtos.PostDto;
import com.abz.pub.domain.dtos.UpdatePostRequestDto;
import com.abz.pub.domain.entities.Post;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping (target = "author", source = "author")
    @Mapping (target = "category", source = "category")
    @Mapping (target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
