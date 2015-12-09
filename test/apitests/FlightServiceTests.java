/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apitests;

import com.google.gson.Gson;
import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import com.jayway.restassured.parsing.Parser;
import dtos.PassengerDto;
import dtos.ReservationDto;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Nikolaj
 */
public class FlightServiceTests {
    
    Gson gson = new Gson();
    
    public FlightServiceTests() {
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "/api";
    }

    @Test
    public void searchTest() {
        get("/flightinfo/CPH/2016-02-01T23:00:00.000Z/2")
                .then()
                .assertThat()
                .body("flights[0].origin", equalTo("CPH"));
    }

    @Test
    public void searchWithDestinationTest() {
        get("/flightinfo/CPH/SXF/2016-02-01T23:00:00.000Z/2")
                .then()
                .assertThat()
                .body("flights[0].destination", equalTo("SXF"));
    }

    @Test
    public void flightReservationTest() {
        List<PassengerDto> passengers = Arrays.asList(new PassengerDto("test name", "test name"));

        ReservationDto reservation = new ReservationDto("test123", null, "test", "test@test.com ", "1234", 1, passengers);
        given()              
                .contentType(JSON)
                .body(gson.toJson(reservation))
                .when()
                .post("/flightreservation")
                .then()
                .statusCode(equalTo(200));
    }
    
    
    @Test
    public void flightReservationNoticketsTest() {
        List<PassengerDto> passengers = Arrays.asList(new PassengerDto("test name", "test name"));

        ReservationDto reservation = new ReservationDto("test1234", null, "test", "test@test.com ", "1234", 2, passengers);
        given()              
                .contentType(JSON)
                .body(gson.toJson(reservation))
                .when()
                .post("/flightreservation")
                .then()
                .statusCode(equalTo(400));
    }
}
