package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
     long getTotalCustomers();
     void register(UserDTO userDTO);
     UserDTO findByUserName(String username);

     void verify(UserDTO userDTO);

}
