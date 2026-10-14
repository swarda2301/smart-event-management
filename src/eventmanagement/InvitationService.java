
package eventmanagement;

import java.util.ArrayList;
import java.util.List;

public class InvitationService {

    private final List<Person> recipients = new ArrayList<>();

    // Add a recipient while preventing duplicate IDs.
    public boolean addRecipient(Person person) {

        for (Person existing : recipients) {
            if (existing.getPersonId().equalsIgnoreCase(
                    person.getPersonId())) {
                return false;
            }
        }

        recipients.add(person);
        return true;
    }

    // Return all recipients.
    public List<Person> getRecipients() {
        return recipients;
    }

    // Display people belonging to a particular role.
    public void displayPeopleByRole(String role) {

        boolean found = false;

        System.out.println("\n===== " + role.toUpperCase()
                + " LIST =====");

        for (Person person : recipients) {

            if (matchesRole(person, role)) {
                System.out.println(
                    "ID: " + person.getPersonId()
                    + " | Name: " + person.getFullName()
                    + " | Email: " + person.getEmail()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No recipients found for this role.");
        }
    }

    // Display invitations for a particular role.
    public void displayInvitationsByRole(Event event, String role) {

        boolean found = false;

        System.out.println("\n===== " + role.toUpperCase()
                + " INVITATIONS =====");

        for (Person person : recipients) {

            if (matchesRole(person, role)) {
                System.out.println("\nTo: " + person.getFullName());
                System.out.println("Email: " + person.getEmail());
                System.out.println(
                    person.getInvitationMessage(event)
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No invitations available for this role.");
        }
    }

    // Display every recipient.
    public void displayRecipientList() {

        System.out.println("\n===== ALL RECIPIENTS =====");

        if (recipients.isEmpty()) {
            System.out.println("No recipients added yet.");
            return;
        }

        for (Person person : recipients) {
            System.out.println(
                "ID: " + person.getPersonId()
                + " | Name: " + person.getFullName()
                + " | Email: " + person.getEmail()
                + " | Role: " + person.getRole()
            );
        }
    }

    // Display every invitation.
    public void displayAllInvitations(Event event) {

        System.out.println("\n===== ALL EVENT INVITATIONS =====");

        if (recipients.isEmpty()) {
            System.out.println("No recipients available.");
            return;
        }

        for (Person person : recipients) {
            System.out.println("\n------------------------------");
            System.out.println("To: " + person.getFullName());
            System.out.println("Email: " + person.getEmail());
            System.out.println(
                person.getInvitationMessage(event)
            );
        }
    }

    // Send invitations to recipients belonging to a selected role.
    public void sendInvitationsByRole(
            Event event,
            String role,
            EmailService emailService) {

        int sent = 0;
        int failed = 0;
        boolean found = false;

        for (Person person : recipients) {

            if (!matchesRole(person, role)) {
                continue;
            }

            found = true;

            try {
                emailService.sendCustomEmail(
                    person.getEmail(),
                    "Invitation: " + event.getEventName(),
                    person.getInvitationMessage(event)
                );

                System.out.println(
                    "Email sent to " + person.getFullName()
                    + " (" + person.getEmail() + ")"
                );

                sent++;

            } catch (Exception e) {
                System.out.println(
                    "Failed to send to " + person.getFullName()
                    + ": " + e.getMessage()
                );

                failed++;
            }
        }

        if (!found) {
            System.out.println(
                "No recipients found for role: " + role
            );
        }

        System.out.println("\n===== EMAIL SENDING SUMMARY =====");
        System.out.println("Successfully sent: " + sent);
        System.out.println("Failed: " + failed);
    }

    // Send invitations to all recipients.
    public void sendAllInvitations(
            Event event,
            EmailService emailService) {

        if (recipients.isEmpty()) {
            System.out.println("No recipients available.");
            return;
        }

        int sent = 0;
        int failed = 0;

        for (Person person : recipients) {
            try {
                emailService.sendCustomEmail(
                    person.getEmail(),
                    "Invitation: " + event.getEventName(),
                    person.getInvitationMessage(event)
                );

                System.out.println(
                    "Email sent to " + person.getFullName()
                );

                sent++;

            } catch (Exception e) {
                System.out.println(
                    "Failed to send to " + person.getFullName()
                    + ": " + e.getMessage()
                );

                failed++;
            }
        }

        System.out.println("\n===== EMAIL SENDING SUMMARY =====");
        System.out.println("Successfully sent: " + sent);
        System.out.println("Failed: " + failed);
    }

    // Match Student and Participant as equivalent roles.
    private boolean matchesRole(Person person, String role) {

        if (person.getRole().equalsIgnoreCase(role)) {
            return true;
        }

        return role.equalsIgnoreCase("Student")
            && person.getRole().equalsIgnoreCase("Participant");
    }
}
