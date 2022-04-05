package com.foodpanda.mappers;

import com.foodpanda.common.LoginDTO;
import com.foodpanda.common.UserDTO;
import com.foodpanda.model.User;

public class UserMapper {

    private static UserMapper userMapper;

    private UserMapper() {

    }

    public static UserMapper getInstance() {

        if (userMapper == null) {
            userMapper = new UserMapper();
        }

        return userMapper;
    }

    public User convertToUser(UserDTO userDTO) {

        User user = new User();

        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());

        return user;
    }

    public UserDTO convertToDTO(User user) {

        if (user == null)
            return null;

        UserDTO userDTO = new UserDTO();

        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public User convertFromLoginDTO(LoginDTO loginDTO) {
        User user = new User();

        user.setUsername(loginDTO.getUsername());
        user.setPassword(loginDTO.getPassword());

        return user;
    }
}
