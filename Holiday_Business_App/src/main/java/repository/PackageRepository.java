package repository;

import model.Destination;
import model.User;
import model.VacationPackage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

public class PackageRepository {

    private static final String FETCH_ALL = "SELECT a FROM VacationPackage a";

    public PackageRepository() {
    }

    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("holiday_app").createEntityManager();
    }

    public void flushAndClear(EntityManager em) {
        em.flush();
        em.clear();
    }

    public String insert(VacationPackage vacationPackage) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        try {
            em.persist(vacationPackage);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            System.out.println("ERROR insert package repo: not unique package");
            return "The name of the pack is already used!";
        }

        em.close();

        return "";
    }

    public String update(VacationPackage vacationPackage) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        em.merge(vacationPackage);
        em.getTransaction().commit();
        em.close();

        return "";
    }

    public boolean delete(Integer id) {
        EntityManager em = this.getEntityManager();

        em.getTransaction().begin();
        VacationPackage vacationPackage = em.find(VacationPackage.class, id);

        if (vacationPackage == null) {
            System.out.println("ERROR delete vacationPackage: id " + id + " not found");
            em.close();
            return false;
        }

        em.remove(vacationPackage);
        flushAndClear(em);
        em.getTransaction().commit();


        em.close();
        return true;
    }

    public VacationPackage findById(Integer id) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        VacationPackage pack = em.find(VacationPackage.class, id);

        em.close();

        return pack;
    }

    public List<VacationPackage> fetchAll() {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        List<VacationPackage> vacationPackages;

        try {
            vacationPackages = em.createQuery(FETCH_ALL, VacationPackage.class).getResultList();
        } catch (NoResultException e) {
            System.out.println("The vacation package table is empty");
            vacationPackages = null;
        }

        em.close();
        return vacationPackages;
    }

    public void clearLinksWithUsers(List<VacationPackage> packages) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        for (VacationPackage pack: packages) {
            for(User user: pack.getUser()) {
                user.getBookedPackages().removeAll(packages);
                em.merge(user);
            }

            pack.setUser(new ArrayList<>());
            em.merge(pack);
            flushAndClear(em);
        }

        em.getTransaction().commit();
        em.close();
    }
}
