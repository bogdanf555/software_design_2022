package com.foodpanda.repository;

import com.foodpanda.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findRestaurantByName(String name);
}
