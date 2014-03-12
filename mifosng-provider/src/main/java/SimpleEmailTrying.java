import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
public class SimpleEmailTrying {

    public void sendToUserAccount() {
        Email email = new SimpleEmail();
        String authuserName = "Rahman Community";
        String authuser = "farhad1238@gmail.com";
        String authpwd ="mujeeb07H11A1238"; 
        email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
        System.out.println("sending email....!");
        email.setDebug(false);
        email.setHostName("smtp.gmail.com");
        try {
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
            email.setFrom(authuser, authuserName);
            
            StringBuilder subjectBuilder = new StringBuilder().append("My first mail").append("lal").append("this is sample code");

            email.setSubject(subjectBuilder.toString());

            String sendToEmail = "mujeeb1238@gmail.com";

            StringBuilder messageBuilder = new StringBuilder().append("hi this is email body ").append(sendToEmail).append("from local system ").append(" :D end of email");

            email.setMsg(messageBuilder.toString());

            email.addTo(sendToEmail, "raghu");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] a){
    	/*new SimpleEmailTrying().sendToUserAccount();*/
    	
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
    	System.out.println(formatter.format(new Date()).toString());
    	
    	System.out.println("email sent.....!");
    }
}