package com.abz.pub.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.abz.pub.domain.PostStatus;
import com.abz.pub.domain.dtos.TagResponse;
import com.abz.pub.domain.entities.Post;
import com.abz.pub.domain.entities.Tag;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    @Mapping (target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponse toTagReponse(Tag tag);

    @Named ("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts){
        if (posts == null) {
            return 0;
        }
        return (int) posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}
