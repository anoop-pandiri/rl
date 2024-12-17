package com.anoop.rl.repository;

import com.anoop.rl.model.TagEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findByListUserUserId(Long userId);
    Long countByListUserUserId(Long userId);
    void deleteByListUserUserId(Long userId);
}
