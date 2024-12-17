package com.anoop.rl.serviceimpl;

import com.anoop.rl.exception.CustomException;
import com.anoop.rl.model.ListEntity;
import com.anoop.rl.model.SortCriterionEntity;
import com.anoop.rl.repository.ListRepository;
import com.anoop.rl.repository.SortCriterionRepository;
import com.anoop.rl.service.SortCriterionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SortCriterionServiceImpl implements SortCriterionService {

    @Autowired
    private SortCriterionRepository sortCriterionRepository;

    @Autowired
    private ListRepository listRepository;

    @Override
    public SortCriterionEntity create(Long listId, SortCriterionEntity sortCriterion) {
        ListEntity list = listRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("List not found with id: " + listId));
        sortCriterion.setList(list);
        return sortCriterionRepository.save(sortCriterion);
    }

    @Override
    public Optional<SortCriterionEntity> findById(Long sortCriterionId) {
        return sortCriterionRepository.findById(sortCriterionId);
    }

    @Override
    public SortCriterionEntity updateById(Long sortCriterionId, String name) {
        SortCriterionEntity sortCriterion = sortCriterionRepository.findById(sortCriterionId)
                .orElseThrow(() -> new CustomException("SortCriterion not found"));

        sortCriterion.setName(name);
        return sortCriterionRepository.save(sortCriterion);
    }

    @Override
    public void deleteById(Long sortCriterionId) {
        SortCriterionEntity sortCriterion = sortCriterionRepository.findById(sortCriterionId)
                .orElseThrow(() -> new CustomException("SortCriterion not found"));

        sortCriterionRepository.delete(sortCriterion);
    }

    @Override
    public List<SortCriterionEntity> findByUserId(Long userId) {
        return sortCriterionRepository.findByListUserUserId(userId);
    }

    @Override
    public Long countByUserId(Long userId) {
        return sortCriterionRepository.countByListUserUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        sortCriterionRepository.deleteByListUserUserId(userId);
    }

    @Override
    public List<SortCriterionEntity> findAll() {
        return sortCriterionRepository.findAll();
    }

    @Override
    public Long countAll() {
        return sortCriterionRepository.count();
    }

    @Override
    public void deleteAll() {
        sortCriterionRepository.deleteAll();
    }
}

