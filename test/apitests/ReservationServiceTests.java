/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apitests;

import com.google.gson.Gson;
import com.jayway.restassured.parsing.Parser;
import org.junit.BeforeClass;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.http.ContentType.JSON;
import com.jayway.restassured.response.Response;
import dtos.LoginDto;
import dtos.PassengerDto;
import dtos.ReservationDto;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

/**
 *
 * @author Nikolaj
 */
public class ReservationServiceTests {

    Gson gson = new Gson();
    private String authTokenUser;
    private String userUsername;
    private String authTokenAdmin;
    private String adminUsername;

    public ReservationServiceTests() {
        Response responseUser = given()
                .contentType(JSON)
                .body(gson.toJson(new LoginDto("user", "test")))
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        authTokenUser = "Bearer " + responseUser.path("token");
        userUsername = responseUser.path("username");
        
        Response responseAdmin = given()
                .contentType(JSON)
                .body(gson.toJson(new LoginDto("admin", "test")))
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        authTokenAdmin = "Bearer " + responseAdmin.path("token");
        adminUsername = responseUser.path("username");
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @Test
    public void getAdminReservationsUnauthorized() {
        get("/reservation")
                .then()
                .assertThat()
                .body("error.code", equalTo(401));
    }

    @Test
    public void getAdminReservationsAuthorized() {
        given()
                .header("Authorization", authTokenAdmin)
                .when()
                .get("/reservation")
                .then()
                .assertThat()
                .statusCode(equalTo(200));
    }

    @Test
    public void getUserReservationsUnauthorized() {
        get("/reservation/user/user")
                .then()
                .assertThat()
                .body("error.code", equalTo(401));
    }
    
     @Test
    public void getUserReservationsAuthorized() {
        given()
                .header("Authorization", authTokenUser)
                .when()
                .get("reservation/user/user")
                .then()
                .assertThat()
                .statusCode(equalTo(200));
    }
    
    @Test
    public void addReservationAuthorized() {
        List<PassengerDto> passengers = Arrays.asList(new PassengerDto("test name", "test name"));
        
        ReservationDto reservation = new ReservationDto("test123",userUsername, "test", "test@test.com ", "1234", 1, passengers);
        given()
                 .header("Authorization", authTokenUser)
                .contentType(JSON)
                .body(gson.toJson(reservation))
                .when()
                .post("/reservation")
                .then()
                .assertThat()
                .statusCode(equalTo(200));        
    }
    
}
