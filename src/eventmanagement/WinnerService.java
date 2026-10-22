
package eventmanagement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WinnerService {

    private final Map<String, Person> winners =
            new LinkedHashMap<>();

    // Select a winner from existing participants.
    public void manageWinners(
            Scanner sc,
            InvitationService service,
            Event event) {

        System.out.println("\n===== MANAGE WINNERS =====");

        System.out.println("\nRegistered Participants:");

        int count = 0;

        for (Person person : service.getRecipients()) {

            if (isParticipant(person)) {
                count++;

                System.out.println(
                        "ID: " + person.getPersonId()
                        + " | Name: " + person.getFullName()
                        + " | Email: " + person.getEmail()
                );
            }
        }

        if (count == 0) {
            System.out.println(
                    "No participants found. Add participants first."
            );
            return;
        }

        System.out.print("\nEnter winner's participant ID: ");
        String id = sc.nextLine().trim();

        Person selected = null;

        for (Person person : service.getRecipients()) {

            if (person.getPersonId().equalsIgnoreCase(id)
                    && isParticipant(person)) {
                selected = person;
                break;
            }
        }

        if (selected == null) {
            System.out.println(
                    "Participant not found. Please check the ID."
            );
            return;
        }

        System.out.println("\nSelect Prize:");
        System.out.println("1. First Prize");
        System.out.println("2. Second Prize");
        System.out.println("3. Third Prize");

        System.out.print("Enter choice: ");
        String choice = sc.nextLine().trim();

        String prize;

        switch (choice) {
            case "1":
                prize = "1st";
                break;

            case "2":
                prize = "2nd";
                break;

            case "3":
                prize = "3rd";
                break;

            default:
                System.out.println("Invalid prize choice.");
                return;
        }

        // Do not assign the same prize twice.
        if (winners.containsKey(prize)) {
            System.out.println(
                    prize + " prize is already assigned to "
                    + winners.get(prize).getFullName() + "."
            );
            return;
        }

        // Do not assign multiple prizes to the same person.
        for (Map.Entry<String, Person> entry
                : winners.entrySet()) {

            if (entry.getValue().getPersonId()
                    .equalsIgnoreCase(selected.getPersonId())) {

                System.out.println(
                        "This participant has already won "
                        + entry.getKey() + " prize."
                );
                return;
            }
        }

        winners.put(prize, selected);

        System.out.println("\nWinner assigned successfully!");
        System.out.println("Name: " + selected.getFullName());
        System.out.println("Participant ID: "
                + selected.getPersonId());
        System.out.println("Prize: " + prize + " Prize");
        System.out.println("Email: " + selected.getEmail());

        System.out.print(
                "\nSend congratulations email now? (yes/no): "
        );

        String answer = sc.nextLine().trim();

        if (answer.equalsIgnoreCase("yes")) {
            sendWinnerEmail(selected, prize, event);
        } else {
            System.out.println(
                    "Winner saved. No email was sent."
            );
        }
    }

    // Identify participants and students.
    private boolean isParticipant(Person person) {

        return person.getRole().equalsIgnoreCase("Participant")
                || person.getRole().equalsIgnoreCase("Student");
    }

    // Send a personalized congratulations email.
    private void sendWinnerEmail(
            Person winner,
            String prize,
            Event event) {

        try {
            EmailService emailService = new EmailService();

            String subject =
                    "Congratulations! You Won "
                    + prize + " Prize - "
                    + event.getEventName();

            String message =
                    "Dear " + winner.getFullName() + ",\n\n"
                    + "Congratulations!\n\n"
                    + "We are delighted to inform you that you "
                    + "have secured the " + prize + " Prize in "
                    + event.getEventName() + ".\n\n"
                    + "Your participation, dedication, and effort "
                    + "are appreciated. We congratulate you on "
                    + "this achievement and wish you success "
                    + "in your future endeavours.\n\n"
                    + "Regards,\n"
                    + event.getEventName()
                    + " Organizing Team";

            emailService.sendCustomEmail(
                    winner.getEmail(),
                    subject,
                    message
            );

            System.out.println(
                    "\nCongratulations email sent successfully "
                    + "to " + winner.getEmail()
            );

        } catch (Exception e) {

            System.out.println(
                    "\nFailed to send congratulations email: "
                    + e.getMessage()
            );

            System.out.println(
                    "The winner is still saved for this session."
            );
        }
    }

    // Display the winners assigned so far.
    public void viewWinners() {

        System.out.println("\n===== WINNERS LIST =====");

        if (winners.isEmpty()) {
            System.out.println("No winners assigned yet.");
            return;
        }

        String[] prizeOrder = {"1st", "2nd", "3rd"};

        for (String prize : prizeOrder) {

            Person person = winners.get(prize);

            if (person != null) {
                System.out.println(
                        prize + " Prize | "
                        + person.getFullName()
                        + " | ID: " + person.getPersonId()
                        + " | Email: " + person.getEmail()
                );
            }
        }
    }
}
