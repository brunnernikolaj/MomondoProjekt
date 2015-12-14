package facades;

import dao.AirportDAO;
import dao.FlightDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dtos.ReservationDto;
import dtos.ReservationResponseDto;
import entity.Airport;
import entity.Flight;
import entity.Passenger;
import entity.Reservation;
import entity.User;
import exceptions.FlightException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ws.rs.core.Response;
import org.joda.time.DateTime;
import org.joda.time.Period;
import us.monoid.json.JSONException;
import utility.NorweigianDestinations;
import static utility.WebScraper.getListOfFlights;

/**
 * Flight facade class.
 *
 * This class handles communication between our rest API and the data concerning
 * the flights.
 *
 * Most queries are handled by the generic DataManager and the rest is handled
 * here, mostly with named queries made in the entity classes.
 *
 * @author casper
 * @Date: 1/12 2015
 */
public class FlightFacade {

    /**
     * Instance of self.
     *
     * Instantiated as null as default.
     */
    public static FlightFacade instance = null;

    FlightDAO flightDAO;
    AirportDAO airportDAO;
    ReservationDAO reservationDAO;
    UserDAO userDAO;

    /**
     * Private constructor.
     */
    private FlightFacade() {
        flightDAO = new FlightDAO();
        airportDAO = new AirportDAO();
        reservationDAO = new ReservationDAO();
        userDAO = new UserDAO();
        
        flightDAO.create(new Flight("CPH", "SXF", "test123", 10, 60, 100.00, new Date()));
        flightDAO.create(new Flight("CPH", "SXF", "test1234", 1, 60, 100.00, new Date()));
    }

    /**
     * Singleton facade.
     *
     * This class is used as a singleton. This could have some performance
     * issues, but for now while we aren't sure, we will use it like this.
     *
     * @author: Casper Schultz
     * @Date: 2/12 2015
     *
     * @return instance as a singleton
     */
    public static FlightFacade getInstance() {
        if (instance == null) {
            instance = new FlightFacade();
        }
        return instance;
    }

    public Flight getByFlightNumber(String flightNumber) throws FlightException {
        return flightDAO.getByFlightNumber(flightNumber);
    }

    /**
     * Returns internal flights.
     *
     * Fetches the internal flights, for a specific from/to destination as IATA
     * code on a given date. If there are no flights in the database, we try to
     * fetch them externally
     *
     * @author: Casper Schultz
     * @Date: 2/12 2015
     *
     * @param from Origin as IATA code
     * @param to Destination as IATA code
     * @param date The date as object to search for
     * @param seats The number of seats required
     * @return List of flight objects that match the criteria
     * @throws exceptions.FlightException
     */
    public List<Flight> getJFFlights(String from, String to, String date, int seats) throws FlightException {

        List<Flight> flights;
        DateTime dt = new DateTime(date);
        Date nextDay = dt.plus(Period.days(1)).toDate();

        // Now we want to check if we have any results in the database, by looking up
        flights = flightDAO.findFlights(from, to, dt.toDate(), nextDay);

        // If no flights where found, we try to lookup the flights at Norweigian
        // and store them for the next time.
        if (flights == null || flights.size() < 1) {

            try {
                flights = getFlightsFromNorweigian(from, to, dt);
                System.out.println("Flights fetched: " + flights.size());
                flightDAO.createFromList(flights);
            } catch (ParseException | IOException e) {
                throw new FlightException("An internal server error occured", Response.Status.INTERNAL_SERVER_ERROR, 4);
            }
        }

        if (flights.size() < 1) {
            throw new FlightException("We have no flights that day from that destination", Response.Status.NO_CONTENT, 1);
        }

        // Return results
        return flights;
    }

    /**
     * Returns internal flights from destination.
     *
     * Our scraper that scrapes Norwegian (and their website) only supports
     * scraping flights that also has a to destination. We could just return
     * nothing, but in order to return as much data as possible, we just fetch
     * from a random destination.
     *
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     *
     * @param from From destination as IATA code
     * @param date Date as String
     * @param seats Number of seats requested
     * @return List of flight objects.
     */
    public List<Flight> getJFFlightsFrom(String from, String date, int seats) throws FlightException {

        List<Flight> flights;
        DateTime dt = new DateTime(date);
        Date nextDay = dt.plus(Period.days(1)).toDate();

        // Now we want to check if we have any results in the database, by looking up
        flights = flightDAO.findFlights(from, dt.toDate(), nextDay);

        // If no flights where found, we try to lookup the flights at Norweigian
        if (flights == null || flights.size() < 1) {

            try {

                // We fetch a random supported destination.
                String destination;
                do {
                    destination = NorweigianDestinations.getRandomDestination();
                } while (destination.equals(from));

                flights = getFlightsFromNorweigian(from, destination, dt);
                flightDAO.createFromList(flights);

            } catch (ParseException | IOException e) {
                throw new FlightException("An internal server error occured", Response.Status.INTERNAL_SERVER_ERROR, 4);
            }
        }

        if (flights.size() < 1) {
            throw new FlightException("We have no flights that day from that destination", Response.Status.NO_CONTENT, 1);
        }

        // Return results
        return flights;
    }

    /**
     *
     * @param reservation
     * @return
     * @throws FlightException
     */
    public Reservation saveReservation(Reservation reservation, String flightId, String userName) throws FlightException {

        Flight flight = getByFlightNumber(flightId);
        if (flight == null) {
            throw new FlightException("invalid flight id", Response.Status.BAD_REQUEST, 3);
        }

        User user = userDAO.find(userName);
        if (user == null) {
            throw new FlightException("invalid user id", Response.Status.BAD_REQUEST, 3);
        }

        if (reservation.getPassengers().size() <= 0) {
            throw new FlightException("An error occured and we could not procedd with the reservation", Response.Status.INTERNAL_SERVER_ERROR, 4);
        }

        flight.addReservation(reservation);
        user.addReservation(reservation);
        reservation.setFlight(flight);
        reservation.setOwner(user);
        reservation.setPrice(reservation.getFlight().getPrice());

        reservationDAO.create(reservation);

        // We also store the reservation with each passenger.
        for (Passenger passenger : reservation.getPassengers()) {
            passenger.addReservation(reservation);
        }

        reservationDAO.update(reservation);
        flightDAO.update(flight);
        userDAO.update(user);

        return reservation;
    }

    /**
     * Reserves a ticket at an external flight company.
     *
     * @Author: Nikolaj
     * @Date: 6/12 2015
     *
     * @param reservation Reservation DTO
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public ReservationResponseDto reserveExternal(ReservationDto reservation) throws IOException, JSONException, FlightException {

        Flight flight = getByFlightNumber(reservation.getFlightID());
        
        if (flight.getNoOfSeats() < reservation.getNumberOfSeats()){
            throw new FlightException("Not enough tickets", Response.Status.BAD_REQUEST, 2);
        }
        
        //Update number of seats on the flight;
        flight.setNoOfSeats(flight.getNoOfSeats() - reservation.getNumberOfSeats());       
        flightDAO.update(flight);
        
        Airport originAirport = airportDAO.getAirportByIATA(flight.getIataFrom());
        Airport destinationAirport = airportDAO.getAirportByIATA(flight.getIataTo());

        String origin = String.format("%s (%s)", originAirport.getCity(),flight.getIataFrom());
        String destination = String.format("%s (%s)", destinationAirport.getCity(),flight.getIataTo());
        
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String time = df.format(flight.getTravelDate());
        
        ReservationResponseDto returnData = new ReservationResponseDto
        (
                reservation.getFlightID(), 
                origin, 
                destination,
                time ,
                flight.getTravelTime() , 
                reservation.getReserveeName(), 
                reservation.getNumberOfSeats(), 
                reservation.getPassengers()
        );

        return returnData;
    }

    public List<Reservation> getAllReservationsByUser(String userName) {
        return reservationDAO.getAllByUser(userName);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();
    }

    /**
     * Fetches flights from Norwegian.
     *
     * Uses the web-scraper to fetch flights externally from a given origin to a
     * destination on a given date.
     *
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     *
     * @param from The origin as IATA code
     * @param to The destination as IATA code
     * @param date The date as a date object
     * @return ArrayList containing flight objects
     * @throws ParseException
     * @throws IOException
     */
    private List<Flight> getFlightsFromNorweigian(String from, String to, DateTime date) throws ParseException, IOException {

        // We need to get the date and format it a bit
        DecimalFormat df = new DecimalFormat("00");
        String day = "" + df.format(date.dayOfMonth().get());
        String month = "" + df.format(date.monthOfYear().get());
        String year = "" + date.year().getAsText();

        String url = "http://www.norwegian.com/uk/booking/flight-tickets/select-flight/?D_City=" + from + "&A_City=" + to + "&TripType=1&D_Day=" + day + "&D_Month=" + year + month + "&D_SelectedDay=" + day + "&R_Day=" + day + "&R_Month=" + year + month + "&R_SelectedDay=" + day + "&CurrencyCode=EUR";

        System.out.println("Fetching from norweigian");
        System.out.println("URL: " + url);

        return getListOfFlights(url);
    }

}
