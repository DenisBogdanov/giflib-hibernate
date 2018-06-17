package com.teamtreehouse.giflib.service;

import com.teamtreehouse.giflib.model.Category;

import java.util.List;

/**
 * Denis, 17.06.2018
 */
public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id);

    void save(Category category);

    void delete(Category category);
}
