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
import org.springframework.web.bind.annotation.RequestParam;

import com.anoop.rl.model.ListEntity;
import com.anoop.rl.service.ListService;

@RequestMapping("/lists")
public class ListController {

    @Autowired
    private ListService listService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ListEntity> createList(
            @PathVariable Long userId, 
            @RequestBody ListEntity list) {
        try {
            ListEntity createdList = listService.create(userId, list);
            return new ResponseEntity<>(createdList, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{listId}")
    public ResponseEntity<ListEntity> getListById(@PathVariable Long listId) {
        Optional<ListEntity> list = listService.findById(listId);
        return list.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<ListEntity> updateListById(
            @PathVariable Long listId, 
            @RequestParam String name) {
        try {
            ListEntity updatedList = listService.updateById(listId, name);
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteListById(@PathVariable Long listId) {
        listService.deleteById(listId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListEntity>> getListsByUserId(@PathVariable Long userId) {
        try {
            List<ListEntity> lists = listService.findByUserId(userId);
            return new ResponseEntity<>(lists, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countListsByUserId(@PathVariable Long userId) {
        Long count = listService.countByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteListsByUserId(@PathVariable Long userId) {
        try {
            listService.deleteByUserId(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ListEntity>> getAllLists() {
        List<ListEntity> lists = listService.findAll();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllLists() {
        Long count = listService.countAll();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllLists() {
        listService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
