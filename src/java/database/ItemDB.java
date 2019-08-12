package database;

import exceptions.ItemDBException;
import models.Item;
import models.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class ItemDB {
    
    public Item getItem(int itemId){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            Item item = em.find(Item.class, itemId);
            return item;
        } finally {
            em.close();
        }
    }
    
    public int insert(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            User owner = item.getOwner();
            owner.getItemList().add(item);
            
            trans.begin();
            em.persist(item);
            em.merge(owner);
            trans.commit();
            
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, "Cannot insert " + item.toString(), ex);

        } finally {
            em.close();
        }
        return 0;
    }

    public int delete(Item item){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            User owner = item.getOwner();
            owner.getItemList().remove(item);
            
            trans.begin();
            em.merge(owner);
            em.remove(em.merge(item));
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, "Cannot delete " + item.toString(), ex);
        } finally {
            em.close();
        }
        return 0;
    }

    public List<Item> getAllForUser(String username) throws ItemDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Item> items = em.createNamedQuery("Item.findAllByOwner", Item.class).setParameter("username", username).getResultList();
            return items;                
        } finally {
            em.close();
        }
    }
    
    public List<Item> getAll() throws ItemDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            return items;                
        } finally {
            em.close();
        }
    }
    
    public int update(Item item) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(item);
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