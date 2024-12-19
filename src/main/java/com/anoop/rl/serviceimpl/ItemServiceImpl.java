package com.anoop.rl.serviceimpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.model.ListEntity;
import com.anoop.rl.repository.ItemRepository;
import com.anoop.rl.repository.ListRepository;
import com.anoop.rl.service.ItemService;

import jakarta.transaction.Transactional;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ListRepository listRepository;

    @Override
    public ItemEntity create(Long listId, ItemEntity Item) {
        Item.setAddedTimestamp(new Timestamp(System.currentTimeMillis()));
        Optional<ListEntity> listOptional = listRepository.findById(listId);
        if (listOptional.isPresent()) {
            ListEntity list = listOptional.get();
            Item.setList(list);
        } else {
            throw new RuntimeException("List not found");
        }
        return itemRepository.save(Item);
    }

    @Override
    public Optional<ItemEntity> findById(Long ItemId) {
        Optional<ItemEntity> optionalItem = itemRepository.findById(ItemId);
        return optionalItem;
    }

    @Override
    public ItemEntity updateById(Long ItemId, String name) {
        Optional<ItemEntity> optionalItem = itemRepository.findById(ItemId);
        if (optionalItem.isPresent()) {
            ItemEntity item = optionalItem.get();
            item.setName(name);
            item.setLastmodifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            return itemRepository.save(item);
        } else {
            throw new RuntimeException("Item not found");
        }
    }

    @Override
    public void deleteById(Long ItemId) {
        itemRepository.deleteById(ItemId);
    }

    @Override
    public List<ItemEntity> findByUserId(Long userId) {
        return itemRepository.findByListUserUserId(userId);
    }

    @Override
    public Long countByUserId(Long userId) {
        return itemRepository.countByListUserUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        itemRepository.deleteByListUserUserId(userId);
    }

    @Override
    public List<ItemEntity> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Long countAll() {
        return itemRepository.count();
    }

    @Override
    public void deleteAll() {
        itemRepository.deleteAll();
    }

    @Transactional
    @Override
    public void bulkUpdateItemPositions(Map<Long, Integer> itemPositions) {
        // Validate input data
        if (itemPositions == null || itemPositions.isEmpty()) {
            throw new IllegalArgumentException("Item positions map cannot be null or empty");
        }

        // Fetch all relevant items in one query
        List<ItemEntity> items = itemRepository.findAllById(itemPositions.keySet());

        // Update positions
        items.forEach(item -> {
            if (itemPositions.containsKey(item.getId())) {
                item.setPosition(itemPositions.get(item.getId()));
            }
        });

        // Save updated items in bulk
        itemRepository.saveAll(items);
    }
    
}
