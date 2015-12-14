package utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import org.joda.time.DateTime;

/**
 * Utility class for common utility helpers.
 *
 * @author casper
 */
public class CommonService {

    /**
     * Get Random Number
     *
     * @param low
     * @param high
     * @return
     */
    public static int getRandomNumber(int low, int high) {

        Random r = new Random();
        int result = r.nextInt(high - low) + low;
        return result;
    }

    /**
     * converts a java HH:mm into minutes.
     *
     * @param time
     * @return
     */
    public static int timeToMins(String time) {
        String[] hourMin = time.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    /**
     * Converts a String in ISO 8601 format to a Date.
     *
     * @Author: Nikolaj
     * @Date: 6/12 2015
     *
     * @param date Date as string
     * @return Date object formatted after ISO 8601
     * @throws ParseException
     */
    public static Date dateFromIsoString(String date) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        Date time = df.parse(date);
        return time;
    }

    /**
     * Converts a Date into Iso 8601 format String
     *
     * @param day
     * @return
     * @throws ParseException
     */
    public static String formatDateIso(Date date){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

}
