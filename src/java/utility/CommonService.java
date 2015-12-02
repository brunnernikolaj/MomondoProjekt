package utility;

import java.util.Random;

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
            int result = r.nextInt(high-low) + low;
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
    
}
