package com.anoop.rl.service;

import com.anoop.rl.model.ListEntity;
import java.util.List;
import java.util.Optional;

public interface ListService {
    // CRUD Operations
    ListEntity create(Long userId, ListEntity list);
    Optional<ListEntity> findById(Long listId);
    Long countItems(Long listId);
    ListEntity updateById(Long listId, String name);
    void deleteById(Long listId);

    // User-Specific Operations
    List<ListEntity> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<ListEntity> findAll();
    Long countAll();
    void deleteAll();
}

