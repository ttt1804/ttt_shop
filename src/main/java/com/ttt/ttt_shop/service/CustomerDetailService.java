package com.ttt.ttt_shop.service;




import com.ttt.ttt_shop.model.entity.CustomerDetail;

import java.util.List;

public interface CustomerDetailService {
    List<CustomerDetail> getAll();
    void add(CustomerDetail customerDetail);

}
