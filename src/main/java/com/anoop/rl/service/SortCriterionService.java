package com.anoop.rl.service;

import java.util.List;
import java.util.Optional;

import com.anoop.rl.model.SortCriterionEntity;

public interface SortCriterionService {

    // CRUD Operations
    SortCriterionEntity create(Long listId, SortCriterionEntity sortCriterion);
    Optional<SortCriterionEntity> findById(Long sortCriterionId);
    SortCriterionEntity updateById(Long sortCriterionId, String name);
    void deleteById(Long sortCriterionId);

    // User-Specific Operations
    List<SortCriterionEntity> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<SortCriterionEntity> findAll();
    Long countAll();
    void deleteAll();

}
