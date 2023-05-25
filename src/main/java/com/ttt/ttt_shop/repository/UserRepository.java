package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    User findUserByUsername(String username);
    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.username = :username")
    User findUserByUsernameWithAuthorities(@Param("username") String username);

    long countUsersByAuthoritiesAuthorityName(String roleCustomer);
}
