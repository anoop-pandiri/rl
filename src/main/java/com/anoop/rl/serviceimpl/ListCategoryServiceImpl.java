package com.anoop.rl.serviceimpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.anoop.rl.model.ListCategoryEntity;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.repository.ListCategoryRepository;
import com.anoop.rl.repository.UserRepository;
import com.anoop.rl.service.ListCategoryService;

public class ListCategoryServiceImpl implements ListCategoryService{

    @Autowired
    private ListCategoryRepository listCategoryRepository;

    @Autowired
    private UserRepository userRepository;
    

    @Override
    public ListCategoryEntity create(Long userId, ListCategoryEntity listCategory) {
        listCategory.setDateAdded(new Timestamp(System.currentTimeMillis()));
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            listCategory.setUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
        return listCategoryRepository.save(listCategory);
    }

    @Override
    public Optional<ListCategoryEntity> findById(Long id) {
        return listCategoryRepository.findById(id);
    }

    @Override
    public ListCategoryEntity updateById(Long id, ListCategoryEntity listCategory) {
        listCategory.setId(id);
        return listCategoryRepository.save(listCategory);
    }

    @Override
    public void deleteById(Long id) {
        listCategoryRepository.deleteById(id);
    }

    @Override
    public List<ListCategoryEntity> findByUserId(Long userId) {
        return listCategoryRepository.findByUser_UserId(userId);
    }

    @Override
    public Long countByUserId(Long userId) {
        return listCategoryRepository.countByUser_UserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        listCategoryRepository.deleteByUser_UserId(userId);
    }

    @Override
    public List<ListCategoryEntity> findAll() {
        return listCategoryRepository.findAll();
    }

    @Override
    public Long count() {
        return listCategoryRepository.count();
    }

    @Override
    public void deleteAll() {
        listCategoryRepository.deleteAll();
    }
    
}
