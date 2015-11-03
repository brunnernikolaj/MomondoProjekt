package facades;

import entity.UserEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFacade {
  
  private final  Map<String, UserEntity> users = new HashMap<>();

  public UserFacade() {
    //Test Users
    UserEntity user = new UserEntity("user","test");
    user.AddRole("User");
    users.put(user.getUserName(),user );
    UserEntity admin = new UserEntity("admin","test");
    admin.AddRole("Admin");
    users.put(admin.getUserName(),admin);
    
    UserEntity both = new UserEntity("user_admin","test");
    both.AddRole("User");
    both.AddRole("Admin");
    users.put(both.getUserName(),both );
  }
  
  public UserEntity getUserByUserId(String id){
    return users.get(id);
  }
  /*
  Return the Roles if users could be authenticated, otherwise null
  */
  public List<String> authenticateUser(String userName, String password){
    UserEntity user = users.get(userName);
    return user != null && user.getPassword().equals(password) ? user.getRoles(): null;
  }
  
}
