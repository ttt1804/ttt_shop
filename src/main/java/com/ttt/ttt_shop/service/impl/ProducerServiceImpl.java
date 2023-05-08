package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.entity.Producer;
import com.ttt.ttt_shop.repository.ProducerRepository;
import com.ttt.ttt_shop.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    ProducerRepository producerRepository;

    @Override
    public List<Producer> getAll() {
        return producerRepository.findAll();
    }
}
