package facades;

import dao.UserDAO;
import entity.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import security.PasswordHash;

public class UserFacade {

    UserDAO dao = new UserDAO();
    private static UserFacade instance = null;

    private UserFacade() {

        if (dao.find("user") == null) {
            //Test Users
            User user = new User("user", "test");
            user.AddRole("User");
            dao.create(user);

            User admin = new User("admin", "test");
            admin.AddRole("Admin");
            dao.create(admin);

            User both = new User("user_admin", "test");
            both.AddRole("User");
            both.AddRole("Admin");
            dao.create(both);
        }
    }

    public static UserFacade getInstance() {
        if (instance == null) {
            instance = new UserFacade();
        }
        return instance;
    }

    public User getUserByUserId(String id) {
        return dao.find(id);
    }
    
    
    public String createNewUser(User user) {
        user.AddRole("User");
        return dao.create(user).getUserName();
    }
    
    public List<String> authenticateUser(String userName, String password) {

        try {
            User user = dao.find(userName);
            return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

        } catch (NullPointerException e) {
            System.out.println("No user found...");
        }

        return null;
    }
}