package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Long> {

    @Query("SELECT u.customerDetail FROM User u INNER JOIN u.authorities a WHERE a.authorityName = 'ROLE_CUSTOMER'")
    List<CustomerDetail> findCustomerDetailsWithRoleCustomer();

}
