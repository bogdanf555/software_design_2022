package controller;

import model.User;
import model.VacationPackage;
import service.UserService;

import java.util.List;

public class UserController {

    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public boolean insertUser(User user) {
        return this.userService.insertUser(user);
    }

    public User findByUsername(String username) {
        return this.userService.findByUsername(username);
    }

    public User login(String username, String password) {
        return this.userService.login(username, password);
    }

    public boolean bookPackage(User user, VacationPackage pack) {
        return this.userService.bookPackage(user, pack);
    }

    public List<VacationPackage> fetchBookedVacations(User user) {
        return this.userService.fetchBookedVacations(user);
    }
}
