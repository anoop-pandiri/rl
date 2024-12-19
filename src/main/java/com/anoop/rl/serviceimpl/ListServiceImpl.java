package com.anoop.rl.serviceimpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anoop.rl.model.ListEntity;
import com.anoop.rl.model.UserEntity;
import com.anoop.rl.repository.ListRepository;
import com.anoop.rl.repository.UserRepository;
import com.anoop.rl.service.ListService;

@Service
public class ListServiceImpl implements ListService{

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ListEntity create(Long userId, ListEntity list) {
        list.setAddedTimestamp(new Timestamp(System.currentTimeMillis()));
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            list.setUser(user);
        } else {
            throw new RuntimeException("User not found");
        }
        return listRepository.save(list);
    }

    @Override
    public Optional<ListEntity> findById(Long listId) {
        Optional<ListEntity> optionalList = listRepository.findById(listId);
        return optionalList;
    }

    @Override
    public Long countItems(Long listId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countItems'");
    }

    @Override
    public ListEntity updateById(Long listId, String name) {
        Optional<ListEntity> optionalList = listRepository.findById(listId);
        if (optionalList.isPresent()) {
            ListEntity list = optionalList.get();
            list.setName(name);
            list.setLastmodifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            return listRepository.save(list);
        } else {
            throw new RuntimeException("List not found");
        }
    }

    @Override
    public void deleteById(Long listId) {
        listRepository.deleteById(listId);
    }

    @Override
    public void sortItems(ListEntity list) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortItems'");
    }

    @Override
    public List<ListEntity> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return listRepository.findByUser(user);
    }

    @Override
    public Long countByUserId(Long userId) {
        return listRepository.countByUser_UserId((long)userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        listRepository.deleteByUser_UserId((long)userId);
    }

    @Override
    public List<ListEntity> findAll() {
        return listRepository.findAll();
    }

    @Override
    public Long countAll() {
        return listRepository.count();
    }

    @Override
    public void deleteAll() {
        listRepository.deleteAll();
    }
    
}
