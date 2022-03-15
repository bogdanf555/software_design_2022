package service;

import model.User;
import model.VacationPackage;
import repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public String validateUser(User user) {

        if (user == null)
            return "User is null";

        return this.validateUsername(user.getUsername());
    }

    public String validateUsername(String username) {
        if (username == null) {
            return "Username is null";
        }

        if (username.isEmpty()) {
            return "Username is empty";
        }
        return "";
    }

    public boolean insertUser(User user) {

        String response = this.validateUser(user);

        if (response.isEmpty()) {
            return this.userRepository.insertUser(user);
        } else {
            System.out.println("ERROR: " + response);
        }

        return false;
    }

    public User findByUsername(String username) {

        String response = this.validateUsername(username);

        if (response.isEmpty()) {
            return this.userRepository.findUserByUsername(username);
        } else {
            System.out.println("ERROR: " + response);
        }

        return null;
    }

    public User login(String username, String password) {
        User user = this.userRepository.findUserByUsername(username);

        if(!user.getPassword().equals(password))
            return null;

        return user;
    }

    public boolean bookPackage(User user, VacationPackage pack) {

        if (user == null || pack == null) {
            System.out.println("ERROR booking: user or package null");
            return false;
        }

        if (pack.getBookings() >= pack.getMaximumBookings()) {
            System.out.println("ERROR booking: not booking available "
                    + pack.getBookings() + " " + pack.getMaximumBookings());
            return false;
        }

        pack.setBookings(pack.getBookings() + 1);
        user.getBookedPackages().add(pack);

        if ( this.userRepository.update(user) ) {
            return true;
        }

        pack.setBookings(pack.getBookings() - 1);
        return false;
    }

    public List<VacationPackage> fetchBookedVacations(User user) {
        if (user == null)
            return null;

        return user.getBookedPackages();
    }

}
