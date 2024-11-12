package com.anoop.rl.repository;

import com.anoop.rl.model.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    int countByListId(Long listId);

    List<Item> findByListIdOrderByPosition(Long listId);

    @Query("SELECT COUNT(i) FROM Item i JOIN i.list l WHERE l.user.userId = :userId")
    long countItemsByUserId(@Param("userId") Integer userId);

    // @Query("SELECT COUNT(*) FROM ITEMS i JOIN LISTS l ON i.list_id_ref = l.list_id WHERE l.user_id_ref = userId")
    //  long countItemsByUserId(Integer userId);

}