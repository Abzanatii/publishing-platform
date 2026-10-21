package com.abz.pub.services;

import java.util.List;
import java.util.UUID;

import com.abz.pub.domain.CreatePostRequest;
import com.abz.pub.domain.UpdatePostRequest;
import com.abz.pub.domain.entities.Post;
import com.abz.pub.domain.entities.User;

public interface PostService {

    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}