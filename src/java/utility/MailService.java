/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Nikolaj
 */
public class MailService {

    //Der skal laves noget her
    public static void sendMail() throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthentication(null, null);
        email.setSSLOnConnect(true);
        email.setSSL(true);
        email.setDebug(true);
        email.setFrom("brunnernikolaj@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("brunnernikolaj@gmail.com");
        email.send();
    }
}
