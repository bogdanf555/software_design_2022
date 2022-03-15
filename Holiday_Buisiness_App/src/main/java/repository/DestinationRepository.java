package repository;

import model.Destination;
import model.VacationPackage;
import org.hibernate.exception.ConstraintViolationException;
import sun.security.krb5.internal.crypto.Des;

import javax.persistence.*;
import java.util.List;

public class DestinationRepository {

    private static final String FETCH_ALL = "SELECT a FROM Destination a";

    public DestinationRepository() {
    }

    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("holiday_app").createEntityManager();
    }

    public void flushAndClear(EntityManager em) {
        em.flush();
        em.clear();
    }

    public boolean insertDestination(Destination destination) {

        EntityManager em = this.getEntityManager();

        em.getTransaction().begin();

        try {
            em.persist(destination);
            em.getTransaction().commit();
        }
        catch (RollbackException e) {
            System.out.println("ERROR: name of the destination is not unique");
            em.close();
            return false;
        }
        catch (PersistenceException e) {
            System.out.println("ERROR: can't persist object that is in db already (hint: update)");
            em.close();
            return false;
        }

        em.close();
        return true;
    }

    public boolean deleteDestination(Integer destinationId) {

        EntityManager em = this.getEntityManager();

        em.getTransaction().begin();
        Destination destination = em.find(Destination.class, destinationId);

        if (destination == null) {
            System.out.println("ERROR delete destination: id " + destinationId + " not found");
            em.close();
            return false;
        }

        em.remove(destination);
        //flushAndClear(em);
        em.getTransaction().commit();


        em.close();
        return true;
    }

    public Destination findById(Integer id) {

        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        Destination dest = em.find(Destination.class, id);

        em.close();

        return dest;
    }

    public List<Destination> fetchAll() {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        List<Destination> destinations;

        try {
            destinations = em.createQuery(FETCH_ALL, Destination.class).getResultList();
        } catch (NoResultException e) {
            System.out.println("The vacation package table is empty");
            destinations = null;
        }

        em.close();

        return destinations;
    }
}
