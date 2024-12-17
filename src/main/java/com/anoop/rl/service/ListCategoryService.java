package com.anoop.rl.service;

import com.anoop.rl.model.ListCategoryEntity;
import java.util.List;
import java.util.Optional;

public interface ListCategoryService {
    // Basic CRUD Operations
    ListCategoryEntity create(Long userId, ListCategoryEntity listCategory);
    Optional <ListCategoryEntity> findById(Long id);
    ListCategoryEntity updateById(Long id, ListCategoryEntity listCategory);
    void deleteById(Long id);

    // User-Specific Operations
    List<ListCategoryEntity> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<ListCategoryEntity> findAll();
    Long count();
    void deleteAll();
}
