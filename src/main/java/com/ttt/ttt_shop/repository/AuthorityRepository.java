package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(String authorityName);
}
