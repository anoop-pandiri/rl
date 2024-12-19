package com.anoop.rl.controller;

import com.anoop.rl.model.TagEntity;
import com.anoop.rl.service.TaggingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-tags")
public class ItemTagController {

    @Autowired
    private TaggingService taggingService;

    // Add a tag to an item
    @PostMapping("/add")
    public ResponseEntity<String> addTagToItem(@RequestParam Long itemId, @RequestParam Long tagId) {
        taggingService.addTagToItem(itemId, tagId);
        return ResponseEntity.ok("Tag added to item successfully.");
    }

    // Remove a tag from an item
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeTagFromItem(@RequestParam Long itemId, @RequestParam Long tagId) {
        taggingService.removeTagFromItem(itemId, tagId);
        return ResponseEntity.ok("Tag removed from item successfully.");
    }

    // Get all tags for an item
    @GetMapping("/tags/{itemId}")
    public ResponseEntity<List<TagEntity>> getTagsForItem(@PathVariable Long itemId) {
        List<TagEntity> tags = taggingService.getTagsForItem(itemId);
        return ResponseEntity.ok(tags);
    }
}