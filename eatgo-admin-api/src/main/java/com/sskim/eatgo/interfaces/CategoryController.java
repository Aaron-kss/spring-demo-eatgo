package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.CategoryService;
import com.sskim.eatgo.domain.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> list(){
        List<Category> categories = categoryService.getCategories();

        return Optional.ofNullable(categories).orElse(Collections.emptyList());
    }

    @PostMapping("/categories")
    public ResponseEntity<?> create(@RequestBody Category resource) throws URISyntaxException {

        Category category = categoryService.addCategory(resource.getName());
        String url = "/categories/" + category.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
