package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public long getTotalCustomers() {
        return userRepository.countUsersByAuthoritiesAuthorityName("ROLE_CUSTOMER");
    }

    @Override
    public void register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setStatus(false);
        user.setVerificationCode(userDTO.getVerificationCode());
        userRepository.save(user);
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findUserByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setVerificationCode(user.getVerificationCode());
        userDTO.setStatus(user.getStatus());
        return  userDTO;
    }

    @Override
    public void verify(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        user.setStatus(true);
        userRepository.save(user);
    }

}
