package com.anoop.rl.service;

import com.anoop.rl.model.ListCategory;
import java.util.List;
import java.util.Optional;

public interface ListCategoryService {
    // Basic CRUD Operations
    ListCategory create(Long userId, ListCategory listCategory);
    Optional <ListCategory> findById(Long id);
    ListCategory updateById(Long id, ListCategory listCategory);
    void deleteById(Long id);

    // User-Specific Operations
    List<ListCategory> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<ListCategory> findAll();
    Long count();
    void deleteAll();
}
