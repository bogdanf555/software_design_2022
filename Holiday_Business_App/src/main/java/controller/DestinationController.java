package controller;

import model.Destination;
import service.DestinationService;

import java.util.List;

public class DestinationController {

    private DestinationService destinationService;

    public DestinationController() {
        this.destinationService = new DestinationService();
    }

    public void insertDestination(Destination destination) {
        this.destinationService.insertDestination(destination);
    }

    public void deleteDestination(Integer destinationId) {
        if (this.destinationService.deleteDestination(destinationId)) {
            System.out.println("destination deleted successfully");
        } else {
            System.out.println("tzapa: destination not deleted");
        }
    }

    public Destination findById(Integer id) {
        return this.destinationService.findById(id);
    }

    public List<Destination> fetchAll() {
        return this.destinationService.fetchAll();
    }
}
