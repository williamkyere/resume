/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mysitev2;

import com.clockworksms.ClockWorkSmsService;
import com.clockworksms.ClockworkException;
import com.clockworksms.ClockworkSmsResult;
import com.clockworksms.SMS;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author aion
 */
public class MessageSessionBean {

    Properties properties = System.getProperties();

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void sendMail(final String fromEmail, final String username, final String password,
            final String toEmail, final String subject, final String message) throws AddressException, MessagingException {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    //getting propertie for mail sessions
                    Properties sysprop = getProperties();
                    Session session = Session.getDefaultInstance(sysprop, new javax.mail.Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            PasswordAuthentication passwordAuthentication = new PasswordAuthentication("williamkyere@openmailbox.org", "KUatae007@24");
                            return passwordAuthentication;
                        }

                    });
                    session.setDebug(true);

                    Message m = new MimeMessage(session);
                    m.setSubject(subject);
                    m.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                    m.setFrom(new InternetAddress(fromEmail));
                    m.setText(message);

                    //sending the message
                    Transport t = session.getTransport("smtp");
                    t.connect("smtp.openmailbox.org", "williamkyere@openmailbox.org", "KUatae007");
                    t.sendMessage(m, m.getAllRecipients());

                } catch (MessagingException ex) {
                    Logger.getLogger(MessageSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        r.run();
    }

    /**
     *
     * @param prop Setting the properties of gmail
     */
    private void setProperties(Properties prop) {
        setProperties(prop, "smtp.openmailbox.org");
    }

    /**
     * 12
     *
     * @param prop target properties
     * @param smtpHost target email server eg smtp.gmail.com
     */
    private void setProperties(Properties props, String smtpHost) {
        //   props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "587");
        //   props.put("mail.smtp.localhost", smtpHost);
        // Session.getInstance(props);
        properties = props;
    }

    public Properties getProperties() {
        setProperties(properties);
        return properties;
    }

    public void sendSMSMessage(String message) throws ClockworkException {
        ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService("fa5214bc0dcb3d884a2a8905434b19277c81ced6 ");

        SMS sms = new SMS("+233279119044", message);
        ClockworkSmsResult result = clockWorkSmsService.send(sms);

        if (result.isSuccess()) {
            System.out.println("Sent with ID: " + result.getId());
        } else {
            System.out.println("Error: " + result.getErrorMessage());
        }
    }
}
