package com.foodpanda.validators;

import com.foodpanda.model.Food;

public class FoodValidator {

    private static FoodValidator foodValidator;

    private FoodValidator() {
    }

    public static FoodValidator getInstance() {

        if (foodValidator == null)
            foodValidator = new FoodValidator();
        return foodValidator;
    }

    public String validateFood(Food food) {

        if (food == null)
            return "food is null";

        if (food.getName() == null || food.getName().isEmpty())
            return "food name not valid";

        if (food.getPrice() == null)
            return "food price is null";

        if (food.getPrice() <= 0)
            return "food price has to be a positive value";

        if (food.getCategory() == null)
            return "food has invalid food category";

        return "";
    }
}
