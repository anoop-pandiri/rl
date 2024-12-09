package com.anoop.rl.repository;

import com.anoop.rl.model.ListCategory;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ListCategoryRepository extends JpaRepository<ListCategory, Long> {
    List<ListCategory> findByUser_UserId(Long userId);
    long countByUser_UserId(Long userId);
    void deleteByUser_UserId(Long userId);
}
