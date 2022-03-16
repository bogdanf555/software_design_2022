package controller;

import model.Destination;
import service.DestinationService;

import java.util.List;

public class DestinationController {

    private DestinationService destinationService;

    public DestinationController() {
        this.destinationService = new DestinationService();
    }

    public String insertDestination(Destination destination) {
        return this.destinationService.insertDestination(destination);
    }

    public String deleteDestination(Integer destinationId) {
        if (!this.destinationService.deleteDestination(destinationId))
            return "Destination not found in database";
        return "";
    }

    public Destination findById(Integer id) {
        return this.destinationService.findById(id);
    }

    public List<Destination> fetchAll() {
        return this.destinationService.fetchAll();
    }
}
