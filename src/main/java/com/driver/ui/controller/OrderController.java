package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.entity.OrderEntity;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;


	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto = orderService.getOrderById(id);

		OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder().orderId(orderDto.getOrderId())
				.userId(orderDto.getUserId()).items(orderDto.getItems()).cost(orderDto.getCost()).
				status(orderDto.isStatus()).build();
		return orderDetailsResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto = orderService.createOrder(OrderDto.builder().userId(order.getUserId()).items(order.getItems())
				.cost(order.getCost()).build());

		OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder().
				orderId(orderDto.getOrderId()).userId(orderDto.getUserId()).items(order.getItems()).
				cost(orderDto.getCost()).status(orderDto.isStatus()).build();

		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto = OrderDto.builder().userId(order.getUserId()).items(order.getItems())
				.cost(order.getCost()).build();
		orderDto = orderService.updateOrderDetails(id,orderDto);

		OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder().
				orderId(orderDto.getOrderId()).userId(orderDto.getUserId()).items(order.getItems()).
				cost(orderDto.getCost()).status(orderDto.isStatus()).build();


		return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		orderService.deleteOrder(id);
		return new OperationStatusModel();
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {

		List<OrderDto> orderDtos = orderService.getOrders();
		List<OrderDetailsResponse> ansOrders= new ArrayList<>();


		for(OrderDto order:orderDtos){
			OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder()
					.orderId(order.getOrderId()).userId(order.getUserId()).
					items(order.getItems()).cost(order.getCost()).status(order.isStatus()).build();

			ansOrders.add(orderDetailsResponse);
		}
		return ansOrders;
	}
}
