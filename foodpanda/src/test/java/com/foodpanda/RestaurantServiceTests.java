package com.foodpanda;

import com.foodpanda.common.RestaurantDTO;
import com.foodpanda.model.Restaurant;
import com.foodpanda.model.User;
import com.foodpanda.repository.RestaurantRepository;
import com.foodpanda.repository.UserRepository;
import com.foodpanda.service.RestaurantService;
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
public class RestaurantServiceTests {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserRepository userRepository;

    List<RestaurantDTO> restaurantDTOList;
    List<Restaurant> restaurantList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        restaurantList = new ArrayList<>();
        restaurantDTOList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            RestaurantDTO restaurantDTO = new RestaurantDTO("i" + i, "i" + i);
            restaurantDTOList.add(restaurantDTO);
            Restaurant restaurant = new Restaurant("i" + i, "i" + i);
            restaurantList.add(restaurant);
        }
    }

    @Test
    public void fetchAll() {
        when(restaurantRepository.findAll()).thenReturn(restaurantList);
        List<RestaurantDTO> returnedRestaurants = restaurantService.fetchAll();

        for (int i = 0; i < 3; i++) {
            assertEquals(returnedRestaurants.get(i).getName(), restaurantDTOList.get(i).getName());
        }
    }

    @Test
    public void fetchRestaurant() {
        User user = new User("bogdan", "test",
                "test@gmail.com",
                "Bogdan Fuia", "admin");

        when(userRepository.findUserByUsername("bogdan"))
                .thenReturn(Optional.of(user));
        when(restaurantRepository.findRestaurantByAdmin(user))
                .thenReturn(Optional.of(new Restaurant("test", "test")));
        RestaurantDTO returnedDTO = restaurantService.fetchRestaurant("bogdan");

        assertEquals(returnedDTO.getName(), "test");
        assertEquals(returnedDTO.getLocation(), "test");
    }

    @Test
    public void insertRestaurant() {
        User user = new User("bogdan", "test",
                "test@gmail.com",
                "Bogdan Fuia", "admin");
        Restaurant restaurant = new Restaurant("test", "test");

        when(userRepository.findUserByUsername("bogdan"))
                .thenReturn(Optional.of(user));
        String response = restaurantService.insertRestaurant(restaurant, "bogdan");

        assertEquals(response, "");
    }
}
