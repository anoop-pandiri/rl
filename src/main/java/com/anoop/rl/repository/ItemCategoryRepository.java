package com.anoop.rl.repository;

import com.anoop.rl.model.ItemCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Integer> {
}