package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.CustomerDetailDTO;
import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.model.entity.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface UserService {
     long getTotalCustomers();
     void register(UserDTO userDTO);
     UserDTO findByUserName(String username);

     Boolean findByEmail(String email);
     Boolean findByUserNameDTO(String username);

     UserDTO findUserByUsernameAndEmail(String username, String email);

     void verify(UserDTO userDTO);

     void updateVerificationCode(String username, String email, String verificationCode);

     void resetPassword(String username, String password);

     User updateInfo(Long id, CustomerDetailDTO customerDetailDTO) throws ParseException;


}
