package com.anoop.rl.service;

import com.anoop.rl.model.ItemEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    // CRUD Operations
    ItemEntity create(Long listId, ItemEntity Item);
    Optional<ItemEntity> findById(Long ItemId);
    ItemEntity updateById(Long ItemId, String name);
    void deleteById(Long ItemId);

    // User-Specific Operations
    List<ItemEntity> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<ItemEntity> findAll();
    Long countAll();
    void deleteAll();

    void bulkUpdateItemPositions(Map<Long, Integer> itemPositions);
}
