package com.anoop.rl.service;

import com.anoop.rl.model.Item;

import java.util.List;

public interface ItemService {
    Item addItemToList(Long listId, Item item);
    void removeItemFromList(Long itemId);
    Item updateItem(Long itemId, Item updatedItem);
    void deleteAllListItems(Long listId);
    Long getTotalItemsCount();
    Long getTotalUserItemsCount(Integer userId);
    void updateItemPositions(Long listId);
    void sortItemPositions(List<Item> list);
    Item getItemById(Long id);
}