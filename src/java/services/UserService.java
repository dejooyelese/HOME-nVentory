package services;

import database.UserDB;
import models.User;
import java.util.List;

public class UserService {

    final UserDB userDB;

    public UserService() {
        userDB = new UserDB();
    }

    public User get(String username) throws Exception {
        return userDB.getUser(username);
    }

    public List<User> getAll() throws Exception {
        return userDB.getAll();
    }

    public int update(String username, String password, String firstname, String lastname, String email, boolean active, boolean isAdmin, String rpuuid, String reguuid) throws Exception {
        User user = get(username);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setActive(active);
        user.setIsAdmin(isAdmin);
        user.setResetPasswordUUID(rpuuid);
        user.setRegistrationUUID(reguuid);
        return userDB.update(user);
    }
    
    public int delete(String username) throws Exception {
        User deletedUser = userDB.getUser(username);
        // do not allow the admin to be deleted
        if (deletedUser.getUsername().equals("admin")) {
            return 0;
        }
        return userDB.delete(deletedUser);
    }

    public User getByEmail(String email) {
        User u = userDB.getUserByEmail(email);
        return u;
    }
    
    public User getByResetPasswordUUID(String uuid) {
        User u = userDB.getUserByResetPasswordUUID(uuid);
        return u;
    }
    
    public User getByRegistrationUUID(String uuid) {
        User u = userDB.getUserByRegistrationUUID(uuid);
        return u;
    }
}
