package com.foodpanda;

import com.foodpanda.common.FoodDTO;
import com.foodpanda.model.Food;
import com.foodpanda.model.Restaurant;
import com.foodpanda.repository.FoodRepository;
import com.foodpanda.repository.RestaurantRepository;
import com.foodpanda.service.FoodService;
import com.foodpanda.utils.FoodCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTests {

    // tested
    @InjectMocks
    private FoodService foodService;

    //dependencies
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private FoodRepository foodRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchAll() {
        List<Food> foods = new ArrayList<>();
        Food food = new Food("test name", "test desc");
        foods.add(food);

        Restaurant testRest = new Restaurant("test", "test");

        when(restaurantRepository.findRestaurantByName("test"))
                .thenReturn(Optional.of(testRest));
        when(foodRepository.findFoodsByRestaurant(testRest))
                .thenReturn(Optional.of(foods));

        List<FoodDTO> foodDTOs = foodService.fetchAll("test");

        assertEquals(foodDTOs.get(0).getName(), "test name");
        assertEquals(foodDTOs.get(0).getDescription(), "test desc");
    }

    @Test
    public void insertFood() {
        Food food = new Food("test food", "test desc", 15.0, FoodCategory.BREAKFAST);
        Restaurant restaurant = new Restaurant("rest", "test");

        when(restaurantRepository.findRestaurantByName(restaurant.getName()))
                .thenReturn(Optional.of(restaurant));
        String response = foodService.insertFood(food, restaurant.getName());

        assertEquals(response, "");
    }

    @Test
    public void fetchAllByCategory() {
        Restaurant restaurant = new Restaurant("rest", "test");
        FoodCategory foodCategory = FoodCategory.BREAKFAST;

        List<Food> foods = new ArrayList<>();
        Food food = new Food("test name", "test desc");
        foods.add(food);

        when(restaurantRepository.findRestaurantByName(restaurant.getName()))
                .thenReturn(Optional.of(restaurant));
        when(foodRepository.findFoodsByRestaurantAndCategory(restaurant, foodCategory))
                .thenReturn(Optional.of(foods));
        List<FoodDTO> foodDTOs = foodService.fetchAllByCategory(restaurant.getName(), foodCategory);

        assertEquals(foodDTOs.get(0).getName(), "test name");
        assertEquals(foodDTOs.get(0).getDescription(), "test desc");
    }
}
