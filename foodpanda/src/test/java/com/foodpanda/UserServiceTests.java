package com.foodpanda;

import com.foodpanda.common.LoginDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.model.User;
import com.foodpanda.repository.UserRepository;
import com.foodpanda.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceTests {

    // tested
    @InjectMocks
    private UserService userService;

    //dependencies
    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void encryptPasswordTest() {

        String password = "parola";
        String expectedHash = "a80b568a237f50391d2f1f97beaf99564e33d2e1c8a2e5cac21ceda701570312";
        String hashed = null;

        try {
            hashed = userService.encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            assert(false);
        }

        assertEquals(expectedHash , hashed);
    }

    @Test
    public void insertUserTest() {

        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedUsername= new String(array, StandardCharsets.UTF_8);

        User user = new User("test", "test",
                "test@gmail.com", "test",
                "user" );
        assertEquals(userService.insertUser(user), "");
    }

    @Test
    public void loginFailTest() {
        LoginDTO loginDTO = new LoginDTO("test", "test");

        when(userRepository.findUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword()))
                .thenReturn(null);

        UserDTO receivedUser = userService.login(loginDTO);

        assertNull(receivedUser);
    }
}
