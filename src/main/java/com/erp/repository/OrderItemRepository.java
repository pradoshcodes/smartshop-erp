package com.erp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
