package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.ProducerDTO;
import com.ttt.ttt_shop.model.entity.Producer;

import java.util.List;

public interface ProducerService {
    List<Producer> getAll();

    ProducerDTO getProducerById(Long id);
    void addProducer(ProducerDTO producerDTO);

    void updateProducer(ProducerDTO producerDTO);

    void deleteProducerById(Long id);
}
