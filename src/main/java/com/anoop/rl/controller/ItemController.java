package com.anoop.rl.controller;

import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // 1. Create Item
    @PostMapping("/create/{listId}")
    public ResponseEntity<ItemEntity> createItem(@PathVariable Long listId, @RequestBody ItemEntity item) {
        ItemEntity createdItem = itemService.create(listId, item);
        return ResponseEntity.ok(createdItem);
    }

    // 2. Find Item by ID
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long itemId) {
        Optional<ItemEntity> optionalItem = itemService.findById(itemId);
        return optionalItem.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    // 3. Update Item Name by ID
    @PutMapping("/update/{itemId}")
    public ResponseEntity<ItemEntity> updateItemById(@PathVariable Long itemId, @RequestParam String name) {
        ItemEntity updatedItem = itemService.updateById(itemId, name);
        return ResponseEntity.ok(updatedItem);
    }

    // 4. Delete Item by ID
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long itemId) {
        itemService.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    // 5. Find All Items by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ItemEntity>> getItemsByUserId(@PathVariable Long userId) {
        List<ItemEntity> items = itemService.findByUserId(userId);
        return ResponseEntity.ok(items);
    }

    // 6. Count Items by User ID
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countItemsByUserId(@PathVariable Long userId) {
        Long count = itemService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    // 7. Delete Items by User ID
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<Void> deleteItemsByUserId(@PathVariable Long userId) {
        itemService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    // 8. Find All Items
    @GetMapping("/all")
    public ResponseEntity<List<ItemEntity>> getAllItems() {
        List<ItemEntity> allItems = itemService.findAll();
        return ResponseEntity.ok(allItems);
    }

    // 9. Count All Items
    @GetMapping("/all/count")
    public ResponseEntity<Long> countAllItems() {
        Long count = itemService.countAll();
        return ResponseEntity.ok(count);
    }

    // 10. Delete All Items
    @DeleteMapping("/all/delete")
    public ResponseEntity<Void> deleteAllItems() {
        itemService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk-sort")
    public ResponseEntity<String> bulkSortItems(@RequestBody Map<Long, Integer> itemPositions) {
        try {
            itemService.bulkUpdateItemPositions(itemPositions);
            return ResponseEntity.ok("Items sorted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while sorting items: " + e.getMessage());
        }
    }
}
