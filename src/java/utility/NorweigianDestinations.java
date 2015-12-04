/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author casper
 */
public class NorweigianDestinations {
    
    public static final String[] DESTINATIONS = {"AGA", "FAO", "ALC", "ALF", "AMS", "ANX", "AYT", "ATH", "BKK", "BCN", "BDU", "BEG", "BGO", "SXF", "BIO", "BHX", "BOO", "BOD", "BOS", "BUD", "BOJ", "CPH", "LCA", "DLM", "STX", "DXB", "DUB", "DBV", "EDI", "FLL", "MCO", "FUE", "GDN", "GVA", "LPA", "GNB", "GOT", "HAM", "EVE", "HAU", "HEL", "IBZ", "SAW", "IVL", "EFL", "KKN", "KRN", "KTT", "CFU", "AJA", "BIA", "KGS", "KRK", "CHQ", "HER", "KRS", "CGN", "LKL", "ACE", "LAS", "PVK", "LIS", "LGW", "LYR", "LAX", "LLA", "FNC", "MAD", "AGP", "PMI", "MMX", "MLA", "MAN", "RAK", "MRS", "MAH", "MXP", "MOL", "TIV", "MPL", "MJV", "MUC", "JFK", "NCE", "OAK", "OSL", "RYG", "OUL", "PLQ", "ORY", "PSA", "PRG", "PRN", "SJU", "PUY", "KEF", "RHO", "RIX", "RJK", "FCO", "RVN", "SZG", "TRF", "JTR", "SJJ", "OLB", "CTA", "PMO", "SPU", "SPU", "SVG", "SZZ", "ARN", "TLL", "TLV", "TFN", "TFS", "TOS", "TRD", "UME", "VAR", "VCE", "VNO", "VBY", "VAA", "WAW", "VIE", "ZAG", "AAL", "AES"};
    
    /**
     * Checks if a given destination is supported.
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
}
