package com.anoop.rl.controller;

import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.model.SortCriterionEntity;
import com.anoop.rl.service.SortingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sorting")
public class SortingController {

    private final SortingService sortingService;

    @Autowired
    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    // 1. Create a new Sort Criterion
    @PostMapping("/criteria")
    public ResponseEntity<SortCriterionEntity> createSortCriterion(
            @RequestParam Long listId,
            @RequestParam String name,
            @RequestParam(required = false) Long baseCriterionId) {

        SortCriterionEntity sortCriterion = sortingService.createSortCriterion(listId, name, baseCriterionId);
        return ResponseEntity.ok(sortCriterion);
    }

    // 2. Get Items Sorted by a Specific Sort Criterion
    @GetMapping("/criteria/{sortCriterionId}/items")
    public ResponseEntity<List<ItemEntity>> getItemsBySortCriterion(@PathVariable Long sortCriterionId) {
        List<ItemEntity> sortedItems = sortingService.getItemsBySortCriterion(sortCriterionId);
        return ResponseEntity.ok(sortedItems);
    }
}
