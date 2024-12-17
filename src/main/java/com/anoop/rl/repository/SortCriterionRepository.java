package com.anoop.rl.repository;

import com.anoop.rl.model.SortCriterionEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface SortCriterionRepository extends JpaRepository<SortCriterionEntity, Long> {
    List<SortCriterionEntity> findByListUserUserId(Long userId);
    Long countByListUserUserId(Long userId);
    void deleteByListUserUserId(Long userId);
}
