package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    User findUserByUsername(String username);
}
