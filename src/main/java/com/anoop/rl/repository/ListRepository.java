package com.anoop.rl.repository;

import com.anoop.rl.model.ListEntity;
import com.anoop.rl.model.UserEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface ListRepository extends JpaRepository<ListEntity, Long> {

    ListEntity findByName(String name);
    List<ListEntity> findByUser(UserEntity user);
    long countByUser_UserId(Long userId);
    void deleteByUser_UserId(Long userId);

}
