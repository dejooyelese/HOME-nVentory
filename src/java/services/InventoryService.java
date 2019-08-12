package services;

import database.CategoryDB;
import exceptions.CategoryDBException;
import exceptions.ItemDBException;
import database.ItemDB;
import database.UserDB;
import models.Category;
import models.Item;
import models.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryService {
    
    final ItemDB itemsDB;
    final CategoryDB categoriesDB;
    final UserDB userDB;
    
    public InventoryService() {
        categoriesDB = new CategoryDB();
        itemsDB = new ItemDB();
        userDB = new UserDB();
    }
    
    public Category getCategory(int categoryID) throws CategoryDBException {
        return categoriesDB.getCategory(categoryID);
    }
    
    public List<Category> getAllCategories() throws CategoryDBException {
        return categoriesDB.getAll();
    }
    
    public int insertCategory(String categoryName) {
        Category newCategory = new Category(0, categoryName);
        return categoriesDB.insert(newCategory);
    }
    
    public int updateCategory(int categoryID, String categoryName) {
        Category categoryToEdit = null;
        try {
            categoryToEdit = categoriesDB.getCategory(categoryID);
        } catch (CategoryDBException ex) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        categoryToEdit.setCategoryName(categoryName);
        return categoriesDB.update(categoryToEdit);
    }
    
    public Item getItem(int itemID) throws ItemDBException {
        return itemsDB.getItem(itemID);
    }
    
    public List<Item> getAllItemsForUser(String username) throws ItemDBException {
        return itemsDB.getAllForUser(username);
    }
    
    public List<Item> getAllItems() throws ItemDBException {
        return itemsDB.getAll();
    }
    
    public int insertItem(String itemname, double price, Category category, User owner) {
        Item newItem = new Item(0, itemname, price);
        newItem.setCategory(category);
        newItem.setOwner(owner);
        return itemsDB.insert(newItem);
    }
    
    public int deleteItem(int itemId, String username) {
        Item itemToDelete = itemsDB.getItem(itemId);
        if(itemToDelete.getOwner().getUsername().equals(username)) {
            return itemsDB.delete(itemToDelete);
        }
        
        return 0;
    }
    
    public int updateItem(String username, int itemId, String name, Double price, Category category) {
        Item itemToEdit = itemsDB.getItem(itemId);
        
        if(!itemToEdit.getOwner().getUsername().equals(username)) {
            return 0;
        }
        
        itemToEdit.setItemName(name);
        itemToEdit.setPrice(price);
        itemToEdit.setCategory(category);
        
        return itemsDB.update(itemToEdit);
    }
}
