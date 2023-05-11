package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.model.dto.ProducerDTO;
import com.ttt.ttt_shop.model.entity.Category;
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

    @Override
    public ProducerDTO getProducerById(Long id) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producer not found with id " + id));
        return convertToDTO(producer);
    }

    @Override
    public void addProducer(ProducerDTO producerDTO) {
        producerRepository.save(convertToEntity(producerDTO));
    }

    @Override
    public void updateProducer(ProducerDTO producerDTO) {
        Producer producer = producerRepository.findById(producerDTO.getId())
                .orElseThrow(() -> new RuntimeException("Producer not found with id " + producerDTO.getId()));
        producer.setName(producerDTO.getName());
        producerRepository.save(producer);
    }

    @Override
    public void deleteProducerById(Long id) {
        producerRepository.deleteById(id);
    }

    private Producer convertToEntity(ProducerDTO producerDTO){
        Producer producer = new Producer();
        producer.setId(producerDTO.getId());
        producer.setName(producerDTO.getName());
        return  producer;
    }
    private ProducerDTO convertToDTO(Producer producer) {
        return new ProducerDTO(producer.getId(), producer.getName());
    }


}
