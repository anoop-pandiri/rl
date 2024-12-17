package com.anoop.rl.service;

import java.util.List;
import java.util.Optional;

import com.anoop.rl.model.TagEntity;

public interface TagService {
    // CRUD Operations
    TagEntity create(Long listId, TagEntity tag);
    Optional<TagEntity> findById(Long tagId);
    TagEntity updateById(Long tagId, String name);
    void deleteById(Long tagId);

    // User-Specific Operations
    List<TagEntity> findByUserId(Long userId);
    Long countByUserId(Long userId);
    void deleteByUserId(Long userId);

    // Admin/Mod Operations
    List<TagEntity> findAll();
    Long countAll();
    void deleteAll();
}
