package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {

        FoodEntity foodEntity = FoodEntity.builder().foodId(food.getFoodId()).foodName(food.getFoodName()).
                foodCategory(food.getFoodCategory()).foodPrice(food.getFoodPrice()).build();
        foodRepository.save(foodEntity);
        return food;

    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {

        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        FoodDto foodDto = FoodDto.builder().id(foodEntity.getId()).foodId(foodEntity.getFoodId())
                .foodName(foodEntity.getFoodName()).foodPrice(foodEntity.getFoodPrice()).
                foodCategory(foodEntity.getFoodCategory()).build();

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodEntity oldFoodEntity = foodRepository.findByFoodId(foodId);

        FoodEntity newFoodEntity = FoodEntity.builder().id(oldFoodEntity.getId()).foodId(foodId).
                foodName(foodDetails.getFoodName()).foodCategory(foodDetails.getFoodCategory()).
                foodPrice(foodDetails.getFoodPrice()).build();

        foodRepository.save(newFoodEntity);

        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {

        foodRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> foodEntities = (List<FoodEntity>) foodRepository.findAll();
        List<FoodDto> foodDtos = new ArrayList<>();

        for(FoodEntity food:foodEntities){
            FoodDto foodDto = FoodDto.builder().id(food.getId()).foodName(food.getFoodName()).
                    foodPrice(food.getFoodPrice()).foodCategory(food.getFoodCategory()).build();

            foodDtos.add(foodDto);
        }
        return foodDtos;
    }


}