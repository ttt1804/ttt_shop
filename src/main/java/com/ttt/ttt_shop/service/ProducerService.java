package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.ProducerDTO;
import com.ttt.ttt_shop.model.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProducerService {
    List<Producer> getAll();

    Page<Producer> getAll(Pageable pageable);

    ProducerDTO getProducerById(Long id);
    void add(ProducerDTO producerDTO);

    void update(ProducerDTO producerDTO);

    void deleteProducerById(Long id);

}
