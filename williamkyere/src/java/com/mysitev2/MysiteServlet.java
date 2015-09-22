package com.mysitev2;

import com.clockworksms.ClockworkException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aion
 */
public class MysiteServlet extends HttpServlet {

    //formServlet
    //attributes;
    private MessageSessionBean msgBean;
    private File messg;
    private Queue<String> msgQueue;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String message = req.getParameter("msg");
        String sendAs = req.getParameter("sendType");

        try {
            //redirect to hompage before sending the message
            resp.sendRedirect("index.html");
            writeToFile(message + "" + email + "" + phone + "" + name);

        } catch (Exception ex) {
            Logger.getLogger(MysiteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // StringBuilder strb = new StringBuilder();
        String mailMessage = name + "\n" + phone + "\n" + email + "\n\n\n" + message;

        //email for sending mail
        String toEmail = "donkor100@Live.com";
        String username = "donkor100@live.com";
        String password = "akwas@24";

        System.out.print(mailMessage);

        msgBean = new MessageSessionBean();
        if (sendAs.equals("EM")) {
            try {
               msgBean.sendMail(toEmail, username, password, email, "Contact through Resume Site!!", mailMessage);

            } catch (Exception ex) {
                Logger.getLogger(MysiteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (sendAs.equalsIgnoreCase("sms")) {
            try {
                //sms code goes here!!
                msgBean.sendSMSMessage(message);
            } catch (ClockworkException ex) {
                Logger.getLogger(MysiteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void writeToFile(String msg) throws IOException {
        msgQueue = new PriorityQueue<String>();
        messg = new File("../newMessages.txt");
        msg+="------------------------\n\n";
        for (String m : msgQueue) {
            new FileWriter(messg).write(m);
        }
        removeMessagesFromQueue(msgQueue);

    }

    private void removeMessagesFromQueue(Queue q) {
        for (Object e : q) {
            q.remove(e);
        }
    }

}
