package acastemi.cars.rest;


import static acastemi.cars.security.Constants.ADMIN;
import static acastemi.cars.security.Constants.USER;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
 
@DeclareRoles({ADMIN, USER})
@ApplicationPath("/")
public class RestApplication extends Application {
}
