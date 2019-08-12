package database;
import exceptions.UserDBException;
import models.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDB {
    
    public int insert(User user) throws UserDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        trans.begin();
        try {
            em.persist(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
                trans.rollback();
        } finally {
            em.close();
        }
        return 1;
    }
    
    public int update(User user) throws UserDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(user);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
        return 1;
    }
    
    public List<User> getAll() throws UserDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;                
        } finally {
            em.close();
        }
    }
    
    public User getUser(String username) throws UserDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
                User user = em.find(User.class, username);
                return user;
        } finally {
                em.close();
        }
    }
    
    public int delete(User user) throws UserDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
        return 1;
    }
    
    public User getUserByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        return user;
    }
    
    public User getUserByResetPasswordUUID(String uuid) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = em.createNamedQuery("User.findByResetPasswordUUID", User.class).setParameter("resetPasswordUUID", uuid).getSingleResult();
        return user;
    }
    
    public User getUserByRegistrationUUID(String uuid) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = em.createNamedQuery("User.findByRegistrationUUID", User.class).setParameter("registrationUUID", uuid).getSingleResult();
        return user;
    }
}