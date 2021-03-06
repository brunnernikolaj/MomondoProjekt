package utility;

import java.util.Random;

/**
 * Utility class for Norwegian destinations.
 * 
 * Norweigian only supports some destinations. In order to spare time
 * when validating, scraping data etc, we store some data and helper methods 
 * here that can make the job easier.
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 */
public class NorweigianDestinations {
    
    public static final String[] DESTINATIONS = {"AGA", "FAO", "ALC", "ALF", "AMS", "ANX", "AYT", "ATH", "BKK", "BCN", "BDU", "BEG", "BGO", "SXF", "BIO", "BHX", "BOO", "BOD", "BOS", "BUD", "BOJ", "CPH", "LCA", "DLM", "STX", "DXB", "DUB", "DBV", "EDI", "FLL", "MCO", "FUE", "GDN", "GVA", "LPA", "GNB", "GOT", "HAM", "EVE", "HAU", "HEL", "IBZ", "SAW", "IVL", "EFL", "KKN", "KRN", "KTT", "CFU", "AJA", "BIA", "KGS", "KRK", "CHQ", "HER", "KRS", "CGN", "LKL", "ACE", "LAS", "PVK", "LIS", "LGW", "LYR", "LAX", "LLA", "FNC", "MAD", "AGP", "PMI", "MMX", "MLA", "MAN", "RAK", "MRS", "MAH", "MXP", "MOL", "TIV", "MPL", "MJV", "MUC", "JFK", "NCE", "OAK", "OSL", "RYG", "OUL", "PLQ", "ORY", "PSA", "PRG", "PRN", "SJU", "PUY", "KEF", "RHO", "RIX", "RJK", "FCO", "RVN", "SZG", "TRF", "JTR", "SJJ", "OLB", "CTA", "PMO", "SPU", "SPU", "SVG", "SZZ", "ARN", "TLL", "TLV", "TFN", "TFS", "TOS", "TRD", "UME", "VAR", "VCE", "VNO", "VBY", "VAA", "WAW", "VIE", "ZAG", "AAL", "AES"};
    
    /**
     * Checks if a given destination is supported.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param dest
     * @return 
     */
    public static boolean validDestination(String dest) {
        
        for (String iata : DESTINATIONS) {
            if (dest.equals(iata)) {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Fetches a random destination.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @return      destination as IATA code
     */
    public static String getRandomDestination() {
        
        Random rand = new Random();
        String dest = DESTINATIONS[rand.nextInt(DESTINATIONS.length)];
        return dest;
    }

}
