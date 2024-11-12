package com.anoop.rl.service;

import com.anoop.rl.model.ListEntity;

import java.util.List;
import java.util.Optional;

public interface ListService {
    ListEntity createList(Integer userId, ListEntity list);
    Long getTotalUserListsCount(Long userId);
    List<ListEntity> getAllUserLists(Integer userId);
    ListEntity renameList(Long id, String name);
    void importLists(List<ListEntity> lists);
    Optional<ListEntity> getListById(Long id);
    void deleteList(Long id);
    void updateListPositions();
    void sortListPositions(List<ListEntity> listEntities);
    //MOD
    List<ListEntity> getAllLists();
    void deleteAllLists();
    Long getTotalListsCount();
    Long getTotalItemsCount(Long listId);
}