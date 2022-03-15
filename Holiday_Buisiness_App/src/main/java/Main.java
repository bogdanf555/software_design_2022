import controller.DestinationController;
import controller.PackageController;
import controller.UserController;
import model.User;
import model.VacationPackage;
import view.AgencyWindow;
import view.MainWindow;

public class Main {

    public static void main(String[] args) {

       /* UserController userController = new UserController();
        DestinationController destinationController = new DestinationController();
        PackageController packageController = new PackageController();

        User user = userController.login("bogdan", "test");

        for (VacationPackage pack : packageController.fetchAvailable()) {
            System.out.println("Package: " + pack.getName());
        }

        VacationPackage pack = packageController.filterByPrice(100.0, "greater").get(0);

        userController.bookPackage(user, pack);

        for(VacationPackage packy : userController.fetchBookedVacations(user)) {
            System.out.println(user + " booked package: " + packy.getName());
        }

        System.out.println(user.getUsername() + " " + user.getFirstName() + " " + user.getLastName());

        System.out.println("Hello world!");*/

        new MainWindow();
    }
}
