package com.anoop.rl.serviceimpl;

import com.anoop.rl.model.ListCategory;
import com.anoop.rl.repository.ListCategoryRepository;
import com.anoop.rl.service.ListCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCategoryServiceImpl implements ListCategoryService {

    @Autowired
    private ListCategoryRepository listCategoryRepository;

    @Override
    public List<ListCategory> getAllListCategories() {
        return listCategoryRepository.findAll();
    }

    @Override
    public ListCategory getListCategoryById(Integer id) {
        return listCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public ListCategory createListCategory(ListCategory listCategory) {
        return listCategoryRepository.save(listCategory);
    }

    @Override
    public ListCategory updateListCategory(Integer id, ListCategory listCategory) {
        listCategory.setLcId(id); // Ensure ID is set for update
        return listCategoryRepository.save(listCategory);
    }

    @Override
    public void deleteListCategory(Integer id) {
        listCategoryRepository.deleteById(id);
    }
}