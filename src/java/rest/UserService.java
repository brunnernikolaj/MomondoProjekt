package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
//@RolesAllowed("Admin")
public class UserService {
  
  UserFacade facade = new UserFacade();
  Gson gson;

    public UserService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
  
    
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getSomething(){
    return gson.toJson(facade.getAll());
  }
 
}