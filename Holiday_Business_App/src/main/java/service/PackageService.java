package service;

import model.Destination;
import model.VacationPackage;
import repository.PackageRepository;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PackageService {

    private PackageRepository packageRepository;

    public PackageService() {
        this.packageRepository = new PackageRepository();
    }

    public String validatePackage(VacationPackage vacationPackage) {

        if (vacationPackage == null) {
            return "the package is null";
        }

        if (vacationPackage.getName() == null) {
            return "the package name is null";
        }

        if (vacationPackage.getName().isEmpty()) {
            return "the package name is empty";
        }

        if (vacationPackage.getPrice() == null) {
            return "the package price is null";
        }

        if (vacationPackage.getPrice() <= 0) {
            return "the package's price must have a positive value";
        }

        if (vacationPackage.getStart() == null || vacationPackage.getEnd() == null) {
            return "the package start or end dates are null";
        }

        if (vacationPackage.getStart().compareTo(vacationPackage.getEnd()) > 0) {
            return "the package's start-end is not valid date interval";
        }

        if (vacationPackage.getMaximumBookings() == null) {
            return "the package's maximum bookings is null";
        }

        if (vacationPackage.getMaximumBookings() <= 0 ) {
            return "the package's maximum bookings must be greater than zero";
        }

        if (vacationPackage.getStart().compareTo(new Date(System.currentTimeMillis())) < 0) {
            System.out.println(vacationPackage.getStart());
            System.out.println(new Date(System.currentTimeMillis()));
            return "the package's date interval is not valid";
        }

        return "";
    }

    public String insertPackage(VacationPackage vacationPackage) {

        String errorMessage = this.validatePackage(vacationPackage);
        if (errorMessage.isEmpty()) {
            return this.packageRepository.insert(vacationPackage);
        } else {
            System.out.println("ERROR insert package: " + errorMessage);
            return errorMessage;
        }
    }

    public String updatePackage(VacationPackage vacationPackage) {

        String errorMessage = this.validatePackage(vacationPackage);
        if (errorMessage.isEmpty()) {
            return this.packageRepository.update(vacationPackage);
        } else {
            System.out.println("ERROR insert package: " + errorMessage);
            return errorMessage;
        }
    }

    public void deletePackage(Integer id) {

        if (id == null) {
            System.out.println("ERROR package service delete: The id is null");
            return;
        }

        this.packageRepository.delete(id);
    }

    public VacationPackage findById(Integer id) {

        return this.packageRepository.findById(id);
    }

    public List<VacationPackage> fetchAll() {
        return this.packageRepository.fetchAll();
    }

    public List<VacationPackage> filterByPrice(Double price, String degreeOfComparison) {

        List<VacationPackage> packages = this.packageRepository.fetchAll();

        if (packages == null) {
            return null;
        }

        Predicate<VacationPackage> byPrice;

        switch (degreeOfComparison) {
            case "greater":
                byPrice = pack -> pack.getPrice() > price;
                break;
            case "equal":
                byPrice = pack -> Objects.equals(pack.getPrice(), price);
                break;
            case "lower":
                byPrice = pack -> pack.getPrice() < price;
                break;
            default:
                return null;
        }

        return packages.stream().filter(byPrice)
                .filter(pack -> pack.getMaximumBookings() > pack.getBookings()).collect(Collectors.toList());
    }

    public List<VacationPackage> filterByPeriod(Date start, Date end) {

        List<VacationPackage> packages = this.packageRepository.fetchAll();

        if (packages == null) {
            return null;
        }

        Predicate<VacationPackage> byPeriod = pack ->
                pack.getStart().compareTo(start) >= 0
                && pack.getEnd().compareTo(end) <= 0;

        return packages.stream().filter(byPeriod)
                .filter(pack -> pack.getMaximumBookings() > pack.getBookings())
                .collect(Collectors.toList());
    }

    public List<VacationPackage> filterByDestination(String destinationName) {

        List<VacationPackage> packages = this.packageRepository.fetchAll();

        if (packages == null) {
            return null;
        }

        Predicate<VacationPackage> byPeriod = pack -> pack.getDestination().getName().equals(destinationName);

        return packages.stream().filter(byPeriod)
                .filter(pack -> pack.getMaximumBookings() > pack.getBookings())
                .collect(Collectors.toList());
    }

    public List<VacationPackage> fetchAvailable() {
        List<VacationPackage> packages = this.packageRepository.fetchAll();

        if (packages == null)
            return null;

        Predicate<VacationPackage> byAvailability = pack -> pack.getBookings() < pack.getMaximumBookings();

        return packages.stream().filter(byAvailability).collect(Collectors.toList());
    }

    public void clearLinksWithUsers(List<VacationPackage> packages) {
        packageRepository.clearLinksWithUsers(packages);
    }
}
