package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.Category;
import com.sskim.eatgo.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        List<Category> categories = categoryRepository.findAll();

        return Optional.ofNullable(categories).orElse(Collections.emptyList());
    }

    public Category addCategory(String name) {

        Category category = Category.builder()
                .name(name)
                .build();

        categoryRepository.save(category);

        return category;
    }
}
