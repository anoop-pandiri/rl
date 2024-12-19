package com.anoop.rl.serviceimpl;

import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.model.ItemTagEntity;
import com.anoop.rl.model.TagEntity;
import com.anoop.rl.repository.ItemRepository;
import com.anoop.rl.repository.ItemTagRepository;
import com.anoop.rl.repository.TagRepository;
import com.anoop.rl.service.TaggingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaggingServiceImpl implements TaggingService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ItemTagRepository itemTagRepository;

    @Override
    public void addTagToItem(Long itemId, Long tagId) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));

        // Check if the tag is already associated with the item
        boolean exists = itemTagRepository.findByItemId(itemId).stream()
                .anyMatch(itemTag -> itemTag.getTag().getId().equals(tagId));
        if (exists) {
            throw new RuntimeException("Tag is already associated with the item.");
        }

        // Create and save the ItemTag relationship
        ItemTagEntity itemTag = new ItemTagEntity();
        itemTag.setItem(item);
        itemTag.setTag(tag);
        itemTagRepository.save(itemTag);
    }

    @Override
    public void removeTagFromItem(Long itemId, Long tagId) {
        List<ItemTagEntity> itemTags = itemTagRepository.findByItemId(itemId).stream()
                .filter(itemTag -> itemTag.getTag().getId().equals(tagId))
                .collect(Collectors.toList());
        if (itemTags.isEmpty()) {
            throw new RuntimeException("Tag is not associated with the item.");
        }

        // Remove all matching relationships
        itemTagRepository.deleteAll(itemTags);
    }

    @Override
    public List<TagEntity> getTagsForItem(Long itemId) {
        return itemTagRepository.findByItemId(itemId).stream()
                .map(ItemTagEntity::getTag)
                .collect(Collectors.toList());
    }
}