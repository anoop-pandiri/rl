package com.anoop.rl.controller;

import com.anoop.rl.model.TagEntity;
import com.anoop.rl.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // 1. Create a new Tag for a List
    @PostMapping("/create/{listId}")
    public ResponseEntity<TagEntity> createTag(@PathVariable Long listId, @RequestBody TagEntity tag) {
        TagEntity createdTag = tagService.create(listId, tag);
        return ResponseEntity.ok(createdTag);
    }

    // 2. Find Tag by ID
    @GetMapping("/{tagId}")
    public ResponseEntity<TagEntity> getTagById(@PathVariable Long tagId) {
        Optional<TagEntity> tag = tagService.findById(tagId);
        return tag.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 3. Update Tag Name by ID
    @PutMapping("/update/{tagId}")
    public ResponseEntity<TagEntity> updateTag(@PathVariable Long tagId, @RequestParam String name) {
        TagEntity updatedTag = tagService.updateById(tagId, name);
        return ResponseEntity.ok(updatedTag);
    }

    // 4. Delete Tag by ID
    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long tagId) {
        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();
    }

    // 5. Find All Tags by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TagEntity>> getTagsByUserId(@PathVariable Long userId) {
        List<TagEntity> tags = tagService.findByUserId(userId);
        return ResponseEntity.ok(tags);
    }

    // 6. Count Tags by User ID
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countTagsByUserId(@PathVariable Long userId) {
        Long count = tagService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    // 7. Delete Tags by User ID
    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<Void> deleteTagsByUserId(@PathVariable Long userId) {
        tagService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    // 8. Find All Tags
    @GetMapping("/all")
    public ResponseEntity<List<TagEntity>> getAllTags() {
        List<TagEntity> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }

    // 9. Count All Tags
    @GetMapping("/all/count")
    public ResponseEntity<Long> countAllTags() {
        Long count = tagService.countAll();
        return ResponseEntity.ok(count);
    }

    // 10. Delete All Tags
    @DeleteMapping("/all/delete")
    public ResponseEntity<Void> deleteAllTags() {
        tagService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
