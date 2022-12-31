package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder().foodId(foodDto.getFoodId())
				.foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).
				foodCategory(foodDto.getFoodCategory()).build();

		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		FoodDto foodDto = foodService.createFood(FoodDto.builder().
				foodName(foodDetails.getFoodName()).foodCategory(foodDetails.getFoodCategory()).
				foodPrice(foodDetails.getFoodPrice()).
				build());

		FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder().foodId(foodDto.getFoodId()).
				foodName(foodDto.getFoodName()).foodCategory(foodDto.getFoodCategory()).
				foodPrice(foodDto.getFoodPrice()).
				build();

		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{

		FoodDto foodDto = FoodDto.builder().foodId(id).foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory()).foodPrice(foodDetails.getFoodPrice()).build();
		foodDto = foodService.updateFoodDetails(id,foodDto);
		FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder().foodId(foodDto.getFoodId()).
				foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).foodCategory(foodDto.getFoodCategory())
				.build();
		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		foodService.deleteFoodItem(id);
		return new OperationStatusModel();
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto> foodDtos = foodService.getFoods();
		List<FoodDetailsResponse> foodAns= new ArrayList<>();

		for(FoodDto food:foodDtos){
			FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder()
					.foodId(food.getFoodId()).foodName(food.getFoodName()).
					foodPrice(food.getFoodPrice()).foodCategory(food.getFoodCategory()).build();

			foodAns.add(foodDetailsResponse);
		}
		return foodAns;
	}
}
