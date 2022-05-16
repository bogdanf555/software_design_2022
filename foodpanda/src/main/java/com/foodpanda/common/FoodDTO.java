package com.foodpanda.common;

import com.foodpanda.utils.FoodCategory;

public class FoodDTO {

    private String name;
    private String description;
    private Double price;
    private FoodCategory foodCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Price: " + price +
                ", Category: " + foodCategory.toString() +
                ", Description: " + description;
    }
}
