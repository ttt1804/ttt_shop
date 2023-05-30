package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.Authority;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.AuthorityRepository;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;
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
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setVerificationCode(user.getVerificationCode());
        userDTO.setStatus(user.getStatus());

        return  userDTO;
    }

    @Override
    public Boolean findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Boolean findByUserNameDTO(String username) {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public UserDTO findUserByUsernameAndEmail(String username, String email) {
        User user = userRepository.findUserByUsernameAndEmail(username, email);
        if(user != null){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            return  userDTO;
        }else{
            return null;
        }
    }

    @Override
    public void verify(UserDTO userDTO) {
        User user = userRepository.findUserByUsername(userDTO.getUsername());
        user.setStatus(true);
        Authority customerAuthority = authorityRepository.findByAuthorityName("ROLE_CUSTOMER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(customerAuthority);
        user.setAuthorities(authorities);
        userRepository.save(user);
    }

    @Override
    public void updateVerificationCode(String username, String email, String verificationCode) {
        User user = userRepository.findUserByUsernameAndEmail(username, email);
        if(user != null) {
            user.setVerificationCode(verificationCode);
            userRepository.save(user);
        }
    }

    @Override
    public void resetPassword(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if(user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}
