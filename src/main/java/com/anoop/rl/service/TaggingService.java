package com.anoop.rl.service;

import java.util.List;

import com.anoop.rl.model.TagEntity;

public interface TaggingService {
    void addTagToItem(Long itemId, Long tagId);
    void removeTagFromItem(Long itemId, Long tagId);
    List<TagEntity> getTagsForItem(Long itemId);
}

