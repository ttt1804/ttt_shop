package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public long getTotalCustomers() {
        return userRepository.countUsersByAuthoritiesAuthorityName("ROLE_CUSTOMER");
    }
}
