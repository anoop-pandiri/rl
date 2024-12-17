package com.anoop.rl.serviceimpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anoop.rl.exception.CustomException;
import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.model.ItemPosition;
import com.anoop.rl.model.ListEntity;
import com.anoop.rl.model.SortCriterionEntity;
import com.anoop.rl.repository.ListRepository;
import com.anoop.rl.repository.SortCriterionRepository;
import com.anoop.rl.service.SortingService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SortingServiceImpl implements SortingService{

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private SortCriterionRepository sortCriterionRepository;

    @Transactional
    public SortCriterionEntity createSortCriterion(Long listId, String name, Long baseCriterionId) {

        ListEntity list = listRepository.findById(listId)
            .orElseThrow(() -> new CustomException("List not found"));

        SortCriterionEntity sortCriterion = new SortCriterionEntity();
        sortCriterion.setName(name);
        sortCriterion.setList(list);

        if (baseCriterionId != null) {
            SortCriterionEntity baseCriterion = sortCriterionRepository.findById(baseCriterionId)
                .orElseThrow(() -> new CustomException("Base SortCriterion not found"));

            baseCriterion.getItemPositions().forEach(basePosition -> {
                ItemPosition newPosition = new ItemPosition();
                newPosition.setSortCriterion(sortCriterion);
                newPosition.setItem(basePosition.getItem());
                newPosition.setPosition(basePosition.getPosition());
                sortCriterion.getItemPositions().add(newPosition);
            });
        } else {
            // Default sorting logic (e.g., by item creation date)
            list.getItems().forEach(item -> {
                ItemPosition newPosition = new ItemPosition();
                newPosition.setSortCriterion(sortCriterion);
                newPosition.setItem(item);
                newPosition.setPosition(item.getId().intValue()); // Example default sort
                sortCriterion.getItemPositions().add(newPosition);
            });
        }

        return sortCriterionRepository.save(sortCriterion);
    }


    public List<ItemEntity> getItemsBySortCriterion(Long sortCriterionId) {
        SortCriterionEntity sortCriterion = sortCriterionRepository.findById(sortCriterionId)
            .orElseThrow(() -> new CustomException("SortCriterion not found"));

        return sortCriterion.getItemPositions().stream()
            .sorted(Comparator.comparingInt(ItemPosition::getPosition))
            .map(ItemPosition::getItem)
            .collect(Collectors.toList());
    }

}