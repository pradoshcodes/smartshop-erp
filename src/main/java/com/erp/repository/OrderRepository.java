package com.erp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

