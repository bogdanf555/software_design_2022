package controller;

import model.Destination;
import model.VacationPackage;
import service.PackageService;

import java.sql.Date;
import java.util.List;

public class PackageController {

    private PackageService packageService;

    public PackageController() {
        this.packageService = new PackageService();
    }

    public String insertPackage(VacationPackage vacationPackage) {

        return this.packageService.insertPackage(vacationPackage);
    }

    public String updatePackage(VacationPackage vacationPackage) {
        return this.packageService.updatePackage(vacationPackage);
    }

    public void deletePackage(Integer id) {
        this.packageService.deletePackage(id);
    }

    public VacationPackage findById(Integer id) { return this.packageService.findById(id); }

    public List<VacationPackage> fetchAll() { return this.packageService.fetchAll(); }

    public List<VacationPackage> fetchAvailable() {return this.packageService.fetchAvailable(); }

    public List<VacationPackage> filterByPrice(Double price, String degreeOfComparison) {
        return this.packageService.filterByPrice(price, degreeOfComparison);
    }

    public List<VacationPackage> filterByPeriod(Date start, Date end) {
        return this.packageService.filterByPeriod(start, end);
    }

    public List<VacationPackage> filterByDestination(String destination) {
        return this.packageService.filterByDestination(destination);
    }

    public void clearLinksWithUsers(List<VacationPackage> packages) {
        this.packageService.clearLinksWithUsers(packages);
    }
}
