package com.abz.pub.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abz.pub.domain.entities.Tag;

//@Repository 
public interface TagRepo extends JpaRepository<Tag, UUID>{

}
