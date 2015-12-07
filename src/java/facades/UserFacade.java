package facades;

import dao.UserDAO;
import entity.UserEntity;
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
            UserEntity user = new UserEntity("user", "test");
            user.AddRole("User");
            dao.create(user);

            UserEntity admin = new UserEntity("admin", "test");
            admin.AddRole("Admin");
            dao.create(admin);

            UserEntity both = new UserEntity("user_admin", "test");
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

    public UserEntity getUserByUserId(String id) {
        return dao.find(id);
    }
    
    
    public String createNewUser(UserEntity user) {
        user.AddRole("User");
        return dao.create(user).getUserName();
    }
    
    public List<String> authenticateUser(String userName, String password) {

        try {
            UserEntity user = dao.find(userName);
            return user != null && PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles() : null;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {

        } catch (NullPointerException e) {
            System.out.println("No user found...");
        }

        return null;
    }
}