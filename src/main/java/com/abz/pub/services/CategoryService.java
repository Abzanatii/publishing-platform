package com.abz.pub.services;

import java.util.List;
import java.util.UUID;

import com.abz.pub.domain.entities.Category;

public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
    Category getCategoryById(UUID id);
}
