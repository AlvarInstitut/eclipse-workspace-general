import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void enviaCorreu(String subject, String body) throws MessagingException {
		
		    String smtpHostServer = "smtp.gmail.com";
		    String adrDesti = "manteniment@ieselcaminas.org";

		    Properties props = System.getProperties();

		    props.put("mail.smtp.host", smtpHostServer);
		    props.put ( "mail.smtp.starttls.enable", "true" );
		    props.put ( "mail.smtp.port", "587" );
		    props.put ( "mail.smtp.auth", "true" );

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("manteniment@ieselcaminas.org", "m@nt3n1m3nt");
				}
			});
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			
				msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
				msg.addHeader("format", "flowed");
				msg.addHeader("Content-Transfer-Encoding", "8bit");

				msg.setSubject(subject, "UTF-8");
				msg.setText(body, "UTF-8");
				msg.setSentDate(new Date());

				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adrDesti, false));
				Transport.send(msg);

				System.out.println("Correu enviat!!");
			
	}
}