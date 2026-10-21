package com.abz.pub.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.abz.pub.domain.entities.Category;
import com.abz.pub.repos.CategoryRepo;
import com.abz.pub.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepo categoryRepo;
    @Override
    public List<Category> listCategories() {
        return categoryRepo.findAll();
    }

    @Override
    @Transactional 
    public Category createCategory(Category category) {
        if (categoryRepo.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isPresent()){
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("This category has posts.");
            }
            categoryRepo.deleteById(id);
        }
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

}
