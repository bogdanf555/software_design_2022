package com.foodpanda.mappers;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.model.Food;
import com.foodpanda.utils.Iterator;

import java.util.ArrayList;
import java.util.List;

public class FoodMapper {

    private static FoodMapper foodMapper;

    private FoodMapper() {
    }

    public static FoodMapper getInstance() {

        if (foodMapper == null) {
            foodMapper = new FoodMapper();
        }
        return foodMapper;
    }

    public FoodDTO convertToDTO(Food food) {

        FoodDTO foodDTO = new FoodDTO();

        foodDTO.setName(food.getName());
        foodDTO.setDescription(food.getDescription());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setFoodCategory(food.getCategory());

        return foodDTO;
    }

    public Food convertFromDTO(FoodDTO foodDTO) {

        Food food = new Food();

        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setPrice(foodDTO.getPrice());
        food.setCategory(foodDTO.getFoodCategory());
        return food;
    }

    public List<FoodDTO> convertListToDTO(List<Food> foods) {

        if (foods == null)
            return null;

        List<FoodDTO> foodDTOS = new ArrayList<>();

        Iterator<Food> foodIterator = new Iterator<Food>(foods);

        while(foodIterator.hasNext()) {
            foodDTOS.add(this.convertToDTO(foodIterator.next()));
        }

        if (foodDTOS.isEmpty())
            return null;

        return foodDTOS;
    }
}
