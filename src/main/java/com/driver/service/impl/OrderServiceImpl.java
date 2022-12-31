package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

   @Autowired
    OrderRepository orderRepository;


    @Override
    public OrderDto createOrder(OrderDto order) {

        OrderEntity orderEntity = OrderEntity.builder().orderId(order.getOrderId()).userId(order.getUserId())
                .items(order.getItems()).cost(order.getCost()).status(order.isStatus()).build();
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = OrderDto.builder().id(orderEntity.getId()).orderId(orderEntity.getOrderId()).
                userId(orderEntity.getUserId()).items(orderEntity.getItems()).cost(orderEntity.getCost()).
                status(orderEntity.isStatus()).build();
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity oldOrderEntity = orderRepository.findByOrderId(orderId);

        OrderEntity newOrderEntity = oldOrderEntity.builder().id(oldOrderEntity.getId()).orderId(order.getOrderId()).
                userId(order.getUserId()).items(order.getItems()).cost(order.getCost()).
                status(order.isStatus()).build();
        orderRepository.save(newOrderEntity);
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        orderRepository.deleteById(Long.parseLong(orderId));
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderEntity> orderEntities = (List<OrderEntity>) orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for(OrderEntity order:orderEntities){
            OrderDto orderDto = OrderDto.builder().id(order.getId()).orderId(order.getOrderId()).
                    userId(order.getUserId()).items(order.getItems()).cost(order.getCost()).
                    status(order.isStatus()).build();

            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}