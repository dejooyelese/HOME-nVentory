package database;

import exceptions.CategoryDBException;
import models.Category;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CategoryDB {
    
    public Category getCategory(int categoryID) throws CategoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Category c = em.find(Category.class, categoryID);
            return c;
        } finally {
            em.close();
        }
    }
    
    public List<Category> getAll() throws CategoryDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categories;
        } finally {
            em.close();
        }
    }
    
    public int insert(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            
            trans.begin();
            em.persist(category);
            trans.commit();
            
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, "Cannot insert " + category.toString(), ex);

        } finally {
            em.close();
        }
        return 0;
    }
    
    public int update(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(category);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
        return 1;
    }
}
