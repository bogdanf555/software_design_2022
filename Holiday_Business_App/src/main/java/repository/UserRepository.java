package repository;

import model.User;
import model.VacationPackage;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class UserRepository {

    String FIND_BY_USERNAME_QUERY = "SELECT u FROM User u WHERE u.username = :username";

    public UserRepository() {
    }

    public EntityManager getEntityManager() {
        return  Persistence.createEntityManagerFactory("holiday_app").createEntityManager();
    }

    public boolean insertUser(User user) {

        EntityManager em = this.getEntityManager();

        em.getTransaction().begin();

        try {
            em.persist(user);
            em.getTransaction().commit();
        }
        catch (RollbackException e) {
            System.out.println("ERROR insert user repo: username or id not unique");
            em.close();
            return false;
        }

        em.close();
        return true;
    }

    public User findUserByUsername(String username) {

        EntityManager em = this.getEntityManager();

        em.getTransaction().begin();

        User user = null;

        try {
            user = em.createQuery(FIND_BY_USERNAME_QUERY, User.class)
                    .setParameter("username", username).getSingleResult();
            em.close();
        } catch (NoResultException e) {
            System.out.println("No user called '" + username + "' was found!");
        }

        return user;
    }

    public boolean update(User user) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("ERROR in user update " + e);
            return false;
        }

        em.close();
        return true;
    }
}
