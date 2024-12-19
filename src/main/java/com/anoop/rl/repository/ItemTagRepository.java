package com.anoop.rl.repository;

import com.anoop.rl.model.ItemTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTagRepository extends JpaRepository<ItemTagEntity, Long> {
    List<ItemTagEntity> findByItemId(Long itemId);
    List<ItemTagEntity> findByTagId(Long tagId);
}
