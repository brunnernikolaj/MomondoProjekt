package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

        resources.add(exceptions.FlightExceptionMapper.class);
        resources.add(exceptions.RestExceptionMapper.class);
        resources.add(rest.AirportService.class);
        resources.add(rest.FlightService.class);
        resources.add(rest.ReservationService.class);
        resources.add(rest.SearchService.class);
        resources.add(rest.UserService.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.Login.class);
        resources.add(security.NotAuthorizedExceptionMapper.class);
        resources.add(security.RegisterService.class);
        resources.add(security.RolesAllowedFilter.class);
    }

}
