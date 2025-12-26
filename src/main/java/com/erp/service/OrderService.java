package com.erp.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.entity.Order;
import com.erp.entity.OrderItem;
import com.erp.entity.Product;
import com.erp.repository.OrderRepository;
import com.erp.repository.ProductRepository;



@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Order order) {

        double total = 0;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItem item : order.getItems()) {

            Product product = productRepository.findById(
                    item.getProduct().getId()
            ).orElseThrow(() -> new RuntimeException("Product not found"));

            // 🔴 STOCK CHECK
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            // ✅ Reduce stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            double subtotal = product.getPrice() * item.getQuantity();

            item.setPrice(product.getPrice());
            item.setSubtotal(subtotal);
            item.setOrder(order);

            total += subtotal;
            items.add(item);
        }

        order.setTotalAmount(total);
        order.setStatus("CREATED");
        order.setItems(items);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
