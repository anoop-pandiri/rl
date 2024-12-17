package com.anoop.rl.serviceimpl;

import com.anoop.rl.exception.CustomException;
import com.anoop.rl.model.ListEntity;
import com.anoop.rl.model.TagEntity;
import com.anoop.rl.repository.ListRepository;
import com.anoop.rl.repository.TagRepository;
import com.anoop.rl.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ListRepository listRepository;

    @Override
    public TagEntity create(Long listId, TagEntity tag) {
        ListEntity list = listRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("List not found with id: " + listId));
        tag.setList(list);
        return tagRepository.save(tag);
    }

    @Override
    public Optional<TagEntity> findById(Long tagId) {
        return tagRepository.findById(tagId);
    }

    @Override
    public TagEntity updateById(Long tagId, String name) {
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new CustomException("Tag not found"));

        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long tagId) {
        TagEntity tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new CustomException("Tag not found"));

        tagRepository.delete(tag);
    }

    @Override
    public List<TagEntity> findByUserId(Long userId) {
        return tagRepository.findByListUserUserId(userId);
    }

    @Override
    public Long countByUserId(Long userId) {
        return tagRepository.countByListUserUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        tagRepository.deleteByListUserUserId(userId);
    }

    @Override
    public List<TagEntity> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Long countAll() {
        return tagRepository.count();
    }

    @Override
    public void deleteAll() {
        tagRepository.deleteAll();
    }
}

