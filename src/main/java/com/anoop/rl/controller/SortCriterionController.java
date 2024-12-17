package com.anoop.rl.controller;

import com.anoop.rl.model.SortCriterionEntity;
import com.anoop.rl.service.SortCriterionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sort-criteria")
public class SortCriterionController {

    @Autowired
    private SortCriterionService sortCriterionService;

    // 1. Create SortCriterion
    @PostMapping("/create/{listId}")
    public ResponseEntity<SortCriterionEntity> createSortCriterion(@PathVariable Long listId, 
                                                                   @RequestBody SortCriterionEntity sortCriterion) {
        SortCriterionEntity created = sortCriterionService.create(listId, sortCriterion);
        return ResponseEntity.ok(created);
    }

    // 2. Find SortCriterion by ID
    @GetMapping("/{sortCriterionId}")
    public ResponseEntity<SortCriterionEntity> getSortCriterionById(@PathVariable Long sortCriterionId) {
        Optional<SortCriterionEntity> optional = sortCriterionService.findById(sortCriterionId);
        return optional.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // 3. Update SortCriterion by ID
    @PutMapping("/update/{sortCriterionId}")
    public ResponseEntity<SortCriterionEntity> updateSortCriterionById(@PathVariable Long sortCriterionId,
                                                                       @RequestParam String name) {
        SortCriterionEntity updated = sortCriterionService.updateById(sortCriterionId, name);
        return ResponseEntity.ok(updated);
    }

    // 4. Delete SortCriterion by ID
    @DeleteMapping("/delete/{sortCriterionId}")
    public ResponseEntity<Void> deleteSortCriterionById(@PathVariable Long sortCriterionId) {
        sortCriterionService.deleteById(sortCriterionId);
        return ResponseEntity.noContent().build();
    }

    // 5. Find SortCriteria by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SortCriterionEntity>> getSortCriteriaByUserId(@PathVariable Long userId) {
        List<SortCriterionEntity> sortCriteria = sortCriterionService.findByUserId(userId);
        return ResponseEntity.ok(sortCriteria);
    }

    // 6. Count SortCriteria by User ID
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countSortCriteriaByUserId(@PathVariable Long userId) {
        Long count = sortCriterionService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    // 7. Delete SortCriteria by User ID
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<Void> deleteSortCriteriaByUserId(@PathVariable Long userId) {
        sortCriterionService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    // 8. Find All SortCriteria
    @GetMapping("/all")
    public ResponseEntity<List<SortCriterionEntity>> getAllSortCriteria() {
        List<SortCriterionEntity> allSortCriteria = sortCriterionService.findAll();
        return ResponseEntity.ok(allSortCriteria);
    }

    // 9. Count All SortCriteria
    @GetMapping("/all/count")
    public ResponseEntity<Long> countAllSortCriteria() {
        Long count = sortCriterionService.countAll();
        return ResponseEntity.ok(count);
    }

    // 10. Delete All SortCriteria
    @DeleteMapping("/all/delete")
    public ResponseEntity<Void> deleteAllSortCriteria() {
        sortCriterionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
