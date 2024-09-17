package com.anoop.rl.serviceimpl;

import com.anoop.rl.model.ApiResponse;
import com.anoop.rl.repository.ApiResponseRepository;
import com.anoop.rl.service.ApiResponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {

    @Autowired
    private ApiResponseRepository apiResponseRepository;

    @Override
    public void saveApiResponse(ApiResponse apiResponse) {
        apiResponseRepository.save(apiResponse);
    }
}
