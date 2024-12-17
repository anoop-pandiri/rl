package com.anoop.rl.service;

import com.anoop.rl.model.ItemEntity;
import com.anoop.rl.model.SortCriterionEntity;
import java.util.List;

public interface SortingService {
    public SortCriterionEntity createSortCriterion(Long listId, String name, Long baseCriterionId);
    public List<ItemEntity> getItemsBySortCriterion(Long sortCriterionId);
}
