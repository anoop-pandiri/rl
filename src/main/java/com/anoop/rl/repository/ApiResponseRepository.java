package com.anoop.rl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anoop.rl.model.ApiResponse;

@Repository
public interface ApiResponseRepository extends JpaRepository<ApiResponse, Long> {
}
