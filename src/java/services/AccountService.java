package services;

import database.UserDB;
import exceptions.UserDBException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {

    UserDB userDB;
    
    public AccountService() {
        this.userDB = new UserDB();
    }
    
    public User login(String username, String password) {
        try {
            User user = userDB.getUser(username);
            
            if (user.getPassword().equals(password) && user.getActive()) {
                // successful login
                //Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "User {0} logged in.", user.getUsername());
                
                return user;
            }
        } catch (UserDBException e) {

        }

        return null;
    }
    
    public boolean resetPassword(String email, String path, String url) {
        
        String uuid = UUID.randomUUID().toString();
        
        String link = url + "?uuid=" + uuid;

        UserService us = new UserService();
        
        User user = us.getByEmail(email);
        
        user.setResetPasswordUUID(uuid);
        
        try {
            us.update(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActive(), user.getIsAdmin(), user.getResetPasswordUUID(), null);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        HashMap<String, String> tagsMap = new HashMap();
        
        tagsMap.put("firstname", user.getFirstName());
        tagsMap.put("lastname", user.getLastName());
        tagsMap.put("link", link);
        
        GmailService.sendMail(email, "Password Reset", path, tagsMap);
        
        return true;
    }
    
    public boolean changePassword(String uuid, String password) {
        UserService us = new UserService();
        try {
            User user = us.getByResetPasswordUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            UserDB ur = new UserDB ();
            ur.update(user);
            return true;
        } catch (UserDBException ex) {
            return false;
        }
    }
    
    public boolean registerAccount(String email, String path, String url) {
        
        String uuid = UUID.randomUUID().toString();
        
        String link = url + "?uuid=" + uuid;

        UserService us = new UserService();
        
        User user = us.getByEmail(email);
        
        user.setRegistrationUUID(uuid);
        
        try {
            us.update(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getActive(), user.getIsAdmin(), null, user.getRegistrationUUID());
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        HashMap<String, String> tagsMap = new HashMap();
        
        tagsMap.put("firstname", user.getFirstName());
        tagsMap.put("lastname", user.getLastName());
        tagsMap.put("link", link);
        
        GmailService.sendMail(email, "Register HOME nVentory Account", path, tagsMap);
        
        return true;
    }
    
    public void welcomeEmail(String email, String path) {
        
        UserService us = new UserService();
        
        User user = us.getByEmail(email);
        
        HashMap<String, String> tagsMap = new HashMap();
        
        tagsMap.put("firstname", user.getFirstName());
        tagsMap.put("lastname", user.getLastName());
        
        GmailService.sendMail(email, "Welcome to HOME nVentory", path, tagsMap);
    }
    
    public User get(String username) throws UserDBException {
        return userDB.getUser(username);
    }
    
    public List<User> getAll() throws UserDBException {
        return userDB.getAll();
    }
    
    public int update(String username, String password, String email, String firstname, String lastname,
            boolean active, boolean isAdmin) throws UserDBException {
        
        User u = userDB.getUser(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setFirstName(firstname);
        u.setLastName(lastname);
        u.setActive(active);
        u.setIsAdmin(isAdmin);
        return userDB.update(u);
    }
    
    public int delete(String actor, String username) throws UserDBException {
        User userToDelete = userDB.getUser(username);
        
        if(!(username.equals(actor))) {
            return userDB.delete(userToDelete);
        }
        return 0;
    }
   
    public int insert(String username, String password, String email, String firstname, String lastname,
            boolean active, boolean isAdmin) throws UserDBException {
        List<User> users = userDB.getAll();
        for(User u: users) {
            if(u.getUsername().equals(username)) {
                return 0;
            }
        }
        User newUser = new User(username, password, email, firstname, lastname, active, isAdmin);
        return userDB.insert(newUser);
    }
}
