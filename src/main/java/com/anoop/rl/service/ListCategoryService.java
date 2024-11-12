package com.anoop.rl.service;

import com.anoop.rl.model.ListCategory;

import java.util.List;

public interface ListCategoryService {
    List<ListCategory> getAllListCategories();
    ListCategory getListCategoryById(Integer id);
    ListCategory createListCategory(ListCategory listCategory);
    ListCategory updateListCategory(Integer id, ListCategory listCategory);
    void deleteListCategory(Integer id);
}