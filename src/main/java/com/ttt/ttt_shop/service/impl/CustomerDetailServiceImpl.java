package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.repository.CustomerDetailRepository;
import com.ttt.ttt_shop.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
    @Autowired
    CustomerDetailRepository customerDetailRepository;
    @Override
    public List<CustomerDetail> getAll() {
        return customerDetailRepository.findCustomerDetailsWithRoleCustomer();
    }
}
