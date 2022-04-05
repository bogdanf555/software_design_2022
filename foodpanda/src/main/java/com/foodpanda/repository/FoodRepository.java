package com.foodpanda.repository;

import com.foodpanda.model.Food;
import com.foodpanda.model.Restaurant;
import com.foodpanda.utils.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    Optional<List<Food>> findFoodsByRestaurantAndCategory(Restaurant restaurant, FoodCategory category);
    Optional<List<Food>> findFoodsByRestaurant(Restaurant restaurant);

}
