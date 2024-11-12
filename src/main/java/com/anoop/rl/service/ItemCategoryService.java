package com.anoop.rl.service;

import com.anoop.rl.model.ItemCategory;

import java.util.List;

public interface ItemCategoryService {
    List<ItemCategory> getAllItemCategories();
    ItemCategory getItemCategoryById(Integer id);
    ItemCategory createItemCategory(ItemCategory ItemCategory);
    ItemCategory updateItemCategory(Integer id, ItemCategory ItemCategory);
    void deleteItemCategory(Integer id);
}
