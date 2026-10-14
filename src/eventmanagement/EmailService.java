
package eventmanagement;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    private final String senderEmail;
    private final String appPassword;

    public EmailService() {
        senderEmail = System.getenv("COLLEGE_EMAIL");
        appPassword = System.getenv("COLLEGE_EMAIL_APP_PASSWORD");

        if (senderEmail == null || senderEmail.isBlank()
                || appPassword == null || appPassword.isBlank()) {
            throw new IllegalStateException(
                "Email credentials are missing. Set COLLEGE_EMAIL "
                + "and COLLEGE_EMAIL_APP_PASSWORD in PowerShell."
            );
        }
    }

    private Session createSession() {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.connectiontimeout", "15000");
        properties.put("mail.smtp.timeout", "15000");
        properties.put("mail.smtp.writetimeout", "15000");

        return Session.getInstance(
            properties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication
                        getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        senderEmail, appPassword
                    );
                }
            }
        );
    }

    public void sendCustomEmail(
            String recipientEmail,
            String subject,
            String body) throws Exception {

        Session session = createSession();
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipient(
            Message.RecipientType.TO,
            new InternetAddress(recipientEmail)
        );
        message.setSubject(subject, "UTF-8");
        message.setText(body, "UTF-8");

        Transport.send(message);
    }

    public void sendInvitation(
            String recipientEmail,
            String recipientName,
            String role,
            String eventName,
            String eventType,
            String date,
            String time,
            String venue,
            String organizer,
            String description) throws Exception {

        String body =
            "Dear " + recipientName + ",\n\n"
            + "You are invited to our event!\n\n"
            + "Event: " + eventName + "\n"
            + "Event Type: " + eventType + "\n"
            + "Date: " + date + "\n"
            + "Time: " + time + "\n"
            + "Venue: " + venue + "\n"
            + "Organizer: " + organizer + "\n"
            + "Your Role: " + role + "\n\n"
            + "Event Description:\n" + description + "\n\n"
            + "We look forward to your participation.\n\n"
            + "Regards,\n" + organizer;

        sendCustomEmail(
            recipientEmail,
            "Invitation: " + eventName,
            body
        );
    }
}
