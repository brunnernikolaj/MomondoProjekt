/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.util.Date;

/**
 *
 * @author Nikolaj
 */
public class FlightRequest {
    
    private String fromIATA;
            
    private String toIATA;
    
    private Date date;
    
    private int ticketAmount;

    public FlightRequest(String fromIATA, String toIATA, Date date, int ticketAmount) {
        this.fromIATA = fromIATA;
        this.toIATA = toIATA;
        this.date = date;
        this.ticketAmount = ticketAmount;
    }
    
    public String getFromIATA() {
        return fromIATA;
    }

    public void setFromIATA(String fromIATA) {
        this.fromIATA = fromIATA;
    }

    public String getToIATA() {
        return toIATA;
    }

    public void setToIATA(String toIATA) {
        this.toIATA = toIATA;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }
    
    
    
}
