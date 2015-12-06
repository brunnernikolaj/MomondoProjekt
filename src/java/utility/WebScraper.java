package utility;

import entity.Flight;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * @author Indian guy + Casper
 */
public class WebScraper {

    public static void main(String[] args) throws Exception {

        String url = "http://www.norwegian.com/dk/booking/fly/valg-flyvning/?D_City=CPH&A_City=ARN&D_Day=30&D_Month=201511&D_SelectedDay=30&R_Day=06&R_Month=201512&R_SelectedDay=06&CurrencyCode=EUR&TripType=2";

        List<Flight> results = getListOfFlights(url);

        if (results != null && results.size() > 0) {
            for (Flight flight : results) {
                System.out.println(flight.toString());
            }
        }
    }

    /**
     * Get List Of Flights
     *
     * @param url
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static List<Flight> getListOfFlights(String url) throws ParseException, IOException {

        //Get IATA Code From Value
        String subFromUrl   = url.substring(url.indexOf("D_City=") + 7);
        String iataFrom     = subFromUrl.substring(0, subFromUrl.indexOf("&"));

        //Get IATA Code To Value
        String subToUrl     = url.substring(url.indexOf("A_City=") + 7);
        String iataTo       = subToUrl.substring(0, subToUrl.indexOf("&"));

        //Get Travel Date
        String subDay = url.substring(url.indexOf("D_Day=") + 6);

        //Get Day Value
        String day          = subDay.substring(0, subDay.indexOf("&"));
        String subMonthYear = url.substring(url.indexOf("D_Month=") + 8);
        String monthYear    = subMonthYear.substring(0, subMonthYear.indexOf("&"));

        //Get Month Value
        String month = monthYear.substring(4);

        //Get Year Value
        String year = monthYear.substring(0, 4);

        //Get Travel Date
        String traveldateInString   = day + "/" + month + "/" + year;
        DateFormat df               = new SimpleDateFormat("dd/MM/yyyy");
        Date travelDate             = df.parse(traveldateInString);

        //Get Page Document
        Document doc = Jsoup.connect(url).get();
        List<Flight> flights = new ArrayList();

        if (doc != null) {
            Elements table = doc.select("table.avadaytable");

            if (table != null && table.size() > 0) {
                Elements trRowInfo1 = table.get(0).select("tr.rowinfo1");
                Elements trRowInfo2 = table.get(0).select("tr.rowinfo2");

                if (trRowInfo1 != null) {
                    for (int i = 0; i < trRowInfo1.size(); i++) {

                        Flight flight = new Flight();
                        
                        // Alot of the information, we can just as easely generate
                        flight.setIataFrom(iataFrom);
                        flight.setIataTo(iataTo);
                        flight.setTravelDate(travelDate);
                        flight.setFlightNumber("JF" + CommonService.getRandomNumber(100, 999));
                        flight.setNoOfSeats(CommonService.getRandomNumber(5, 300));

                        //Get Price from page source
                        Elements rawInfo1tds = trRowInfo1.get(i).select("td.standardlowfare label.seatsokfare");
                        String price = "0";

                        if (rawInfo1tds != null && rawInfo1tds.size() > 0) {
                            price = rawInfo1tds.get(0).html();
                        } else {
                            rawInfo1tds = trRowInfo1.get(i).select("td.standardlowfare label.fewseatsleftfare");
                            if (rawInfo1tds != null && rawInfo1tds.size() > 0) {
                                price = rawInfo1tds.get(0).html();
                            }
                        }
                        
                        //Set Price Value
                        flight.setPrice(Double.parseDouble(price));

                        //Get From Time or To Time
                        String duration         = trRowInfo2.get(i).select("td.duration div.content").text();
                        
                        try {
                            String[] str = duration.split(" ");
                            int hours = Integer.parseInt(str[1].replaceAll("[^\\d.]", ""));
                            int minutes = Integer.parseInt(str[2].replaceAll("[^\\d.]", ""));

                            if (hours > 0) 
                                flight.setTravelTime(hours * minutes + minutes);
                            else 
                                flight.setTravelTime(minutes);
                        } catch (Exception e) {
                            flight.setTravelTime(0);
                        }
                        
                        //Add flight object to List
                        flights.add(flight);
                    }
                }
            }
        }

        return flights;
    }
}
