package com.abz.pub.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abz.pub.domain.PostStatus;
import com.abz.pub.domain.entities.Post;
import com.abz.pub.domain.entities.Tag;
import com.abz.pub.domain.entities.User;
import com.abz.pub.domain.entities.Category;


//@Repository 
public interface PostRepo extends JpaRepository<Post, UUID>{
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status,
        Category category, Tag tag);

    List<Post> findAllByStatusAndCategory(PostStatus status,
        Category category);

    List<Post> findAllByStatusAndTagsContaining(PostStatus status,
        Tag tag);

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
    
}
