package com.anoop.rl.repository;

import com.anoop.rl.model.ItemEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByListUserUserId(Long userId);
    Long countByListUserUserId(Long userId);
    @Transactional
    void deleteByListUserUserId(Long userId);
}
