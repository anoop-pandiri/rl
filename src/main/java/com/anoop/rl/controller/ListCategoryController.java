package com.anoop.rl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anoop.rl.model.ListCategory;
import com.anoop.rl.service.ListCategoryService;

@RequestMapping("/list-categories")
public class ListCategoryController {

    @Autowired
    private ListCategoryService listCategoryService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ListCategory> createListCategory(
            @PathVariable Long userId,
            @RequestBody ListCategory listCategory) {
        try {
            ListCategory createdListCategory = listCategoryService.create(userId, listCategory);
            return new ResponseEntity<>(createdListCategory, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListCategory> getListCategoryById(@PathVariable Long id) {
        Optional<ListCategory> listCategory = listCategoryService.findById(id);
        return listCategory
                .map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListCategory> updateListCategoryById(
            @PathVariable Long id,
            @RequestBody ListCategory listCategory) {
        try {
            ListCategory updatedListCategory = listCategoryService.updateById(id, listCategory);
            return new ResponseEntity<>(updatedListCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListCategoryById(@PathVariable Long id) {
        listCategoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListCategory>> getListCategoriesByUserId(@PathVariable Long userId) {
        List<ListCategory> listCategories = listCategoryService.findByUserId(userId);
        return new ResponseEntity<>(listCategories, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countListCategoriesByUserId(@PathVariable Long userId) {
        Long count = listCategoryService.countByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteListCategoriesByUserId(@PathVariable Long userId) {
        listCategoryService.deleteByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ListCategory>> getAllListCategories() {
        List<ListCategory> listCategories = listCategoryService.findAll();
        return new ResponseEntity<>(listCategories, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllListCategories() {
        Long count = listCategoryService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllListCategories() {
        listCategoryService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
