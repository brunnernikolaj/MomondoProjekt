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
    
}
