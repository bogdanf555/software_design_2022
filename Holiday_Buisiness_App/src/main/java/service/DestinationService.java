package service;

import model.Destination;
import repository.DestinationRepository;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

public class DestinationService {

    private DestinationRepository destinationRepository;

    public DestinationService() {
        this.destinationRepository = new DestinationRepository();
    }

    public void insertDestination(Destination destination) {
        if (destination.getName() != null && !destination.getName().isEmpty())
            this.destinationRepository.insertDestination(destination);
        else
            System.out.println("ERROR insert destination: name empty or null");
    }

    public boolean deleteDestination(Integer destinationId) {
        return this.destinationRepository.deleteDestination(destinationId);
    }

    public Destination findById(Integer id) {
        return this.destinationRepository.findById(id);
    }

    public List<Destination> fetchAll() { return this.destinationRepository.fetchAll();}
}
