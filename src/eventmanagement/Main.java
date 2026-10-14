
package eventmanagement;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("       SMART EVENT MANAGEMENT SYSTEM");
        System.out.println("==========================================");

        System.out.println("\n--- CREATE NEW EVENT ---");

        System.out.print("Enter Event ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Event Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Event Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        System.out.print("Enter Time: ");
        String time = sc.nextLine();

        System.out.print("Enter Venue: ");
        String venue = sc.nextLine();

        System.out.print("Enter Organizer Name: ");
        String organizer = sc.nextLine();

        System.out.print("Enter Event Description: ");
        String description = sc.nextLine();

        Event event = new Event(
                id, name, type, date, time,
                venue, organizer, description
        );

        event.displayEventDetails();

        System.out.print("\nEnter maximum venue capacity: ");
        int capacity = readPositiveInt(sc);

        event.setVenueManager(
                new Venue("VENUE-1", venue, capacity)
        );

        System.out.print("Enter number of entry gates: ");
        int gateCount = readPositiveInt(sc);

        for (int i = 1; i <= gateCount; i++) {

            System.out.println("\nGate " + i);

            System.out.print("Enter gate ID: ");
            String gateId = sc.nextLine();

            System.out.print("Enter gate name: ");
            String gateName = sc.nextLine();

            System.out.print("Enter gate capacity: ");
            int gateCapacity = readPositiveInt(sc);

            event.addGate(
                    new EntryGate(gateId, gateName, gateCapacity)
            );
        }

        InvitationService service = new InvitationService();
        EventManager manager = new EventManager(event);
        ExcelImportService excelService = new ExcelImportService();

        boolean running = true;

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Add Student / Participant");
            System.out.println("2. Add Judge");
            System.out.println("3. Add Teacher");
            System.out.println("4. View Separate Lists");
            System.out.println("5. View Separate Invitations");
            System.out.println("6. View All Recipients");
            System.out.println("7. View All Invitations");
            System.out.println("8. View Event Details");
            System.out.println("9. Register Regular Attendee");
            System.out.println("10. Register VIP Attendee");
            System.out.println("11. Register Staff Attendee");
            System.out.println("12. Add Attendee to Entry Queue");
            System.out.println("13. Process Next Entry");
            System.out.println("14. Process Attendee Exit");
            System.out.println("15. View Waiting Queue");
            System.out.println("16. View Gate Status");
            System.out.println("17. View Crowd Status");
            System.out.println("18. View Entry / Exit Records");
            System.out.println("19. Upload People from Excel");
            System.out.println("20. View Attendance Report");
            System.out.println("21. Send Student / Participant Invitations");
            System.out.println("22. Send Teacher Invitations");
            System.out.println("23. Send Judge Invitations");
            System.out.println("24. Send All Invitations");
            System.out.println("25. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":
                    addPerson(sc, service, "Participant");
                    break;

                case "2":
                    addPerson(sc, service, "Judge");
                    break;

                case "3":
                    addPerson(sc, service, "Teacher");
                    break;

                case "4":
                    viewSeparateLists(sc, service);
                    break;

                case "5":
                    viewSeparateInvitations(sc, service, event);
                    break;

                case "6":
                    service.displayRecipientList();
                    break;

                case "7":
                    service.displayAllInvitations(event);
                    break;

                case "8":
                    event.displayEventDetails();
                    break;

                case "9":
                    registerAttendee(sc, event, "REGULAR");
                    break;

                case "10":
                    registerAttendee(sc, event, "VIP");
                    break;

                case "11":
                    registerAttendee(sc, event, "STAFF");
                    break;

                case "12":
                    System.out.print("Enter attendee ID: ");
                    manager.addAttendeeToQueue(sc.nextLine().trim());
                    break;

                case "13":
                    System.out.print("Enter gate ID: ");
                    manager.processNextEntry(sc.nextLine().trim());
                    break;

                case "14":
                    System.out.print("Enter attendee ID: ");
                    manager.processExit(sc.nextLine().trim());
                    break;

                case "15":
                    manager.showQueue();
                    break;

                case "16":
                    manager.showGates();
                    break;

                case "17":
                    manager.showCrowdStatus();
                    break;

                case "18":
                    manager.showRecords();
                    break;

                case "19": {

                    System.out.println("\n--- EXCEL IMPORT ---");
                    System.out.print(
                            "Enter the full path of your .xlsx file: "
                    );

                    String filePath = sc.nextLine().trim();

                    if (filePath.startsWith("\"")
                            && filePath.endsWith("\"")
                            && filePath.length() >= 2) {
                        filePath = filePath.substring(
                                1, filePath.length() - 1
                        );
                    }

                    excelService.importPeople(filePath, service);

                    int registered = 0;

                    for (Person person : service.getRecipients()) {

                        if (!"Participant".equalsIgnoreCase(
                                person.getRole())) {
                            continue;
                        }

                        if (event.findAttendee(
                                person.getPersonId()) != null) {
                            continue;
                        }

                        Attendee attendee = new RegularAttendee(
                                person.getPersonId(),
                                person.getFullName(),
                                person.getEmail()
                        );

                        event.addAttendee(attendee);
                        registered++;
                    }

                    System.out.println(
                            "New participants registered as attendees: "
                                    + registered
                    );

                    System.out.println(
                            "Choose option 20 to view the attendance report."
                    );

                    break;
                }

                case "20":
                    showAttendanceReport(event);
                    break;

                case "21":
                case "22":
                case "23":
                case "24": {

                    String role;

                    switch (choice) {
                        case "21":
                            role = "Participant";
                            break;

                        case "22":
                            role = "Teacher";
                            break;

                        case "23":
                            role = "Judge";
                            break;

                        default:
                            role = "ALL";
                            break;
                    }

                    System.out.println(
                            "\n--- SEND EMAIL INVITATIONS ---"
                    );

                    try {
                        EmailService emailService = new EmailService();

                        if ("ALL".equals(role)) {
                            System.out.println(
                                    "Sending invitations to all recipients..."
                            );

                            service.sendAllInvitations(
                                    event, emailService
                            );

                        } else {
                            System.out.println(
                                    "Sending invitations to: " + role
                            );

                            service.sendInvitationsByRole(
                                    event, role, emailService
                            );
                        }

                    } catch (IllegalStateException e) {
                        System.out.println(
                                "Email configuration error: "
                                        + e.getMessage()
                        );
                    }

                    break;
                }

                case "25":
                    running = false;
                    System.out.println(
                            "Thank you for using the system!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Enter a number from 1 to 25."
                    );
            }
        }

        sc.close();
    }

    private static int readPositiveInt(Scanner sc) {

        while (true) {
            try {
                int value = Integer.parseInt(
                        sc.nextLine().trim()
                );

                if (value > 0) {
                    return value;
                }

            } catch (NumberFormatException ignored) {
                // Ask again if the input is invalid.
            }

            System.out.print(
                    "Enter a valid positive whole number: "
            );
        }
    }

    private static void registerAttendee(
            Scanner sc, Event event, String type) {

        System.out.println(
                "\n--- REGISTER " + type + " ATTENDEE ---"
        );

        System.out.print("Enter attendee ID: ");
        String userId = sc.nextLine().trim();

        if (userId.isEmpty()) {
            System.out.println("Attendee ID cannot be empty.");
            return;
        }

        if (event.findAttendee(userId) != null) {
            System.out.println("That attendee ID already exists.");
            return;
        }

        System.out.print("Enter full name: ");
        String fullName = sc.nextLine().trim();

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine().trim();

        if (fullName.isEmpty() || contact.isEmpty()) {
            System.out.println(
                    "Name and contact number cannot be empty."
            );
            return;
        }

        Attendee attendee;

        switch (type) {
            case "VIP":
                attendee = new VIPAttendee(
                        userId, fullName, contact
                );
                break;

            case "STAFF":
                attendee = new Staff(
                        userId, fullName, contact
                );
                break;

            default:
                attendee = new RegularAttendee(
                        userId, fullName, contact
                );
                break;
        }

        event.addAttendee(attendee);
        attendee.displayDetails();

        System.out.println("Attendee registered successfully.");
    }

    private static void addPerson(
            Scanner sc, InvitationService service, String role) {

        System.out.println(
                "\n--- ADD " + role.toUpperCase() + " ---"
        );

        System.out.print("Enter Person ID: ");
        String personId = sc.nextLine().trim();

        System.out.print("Enter Full Name: ");
        String fullName = sc.nextLine().trim();

        System.out.print("Enter Email Address: ");
        String email = sc.nextLine().trim();

        if (personId.isEmpty()
                || fullName.isEmpty()
                || email.isEmpty()
                || !email.matches(
                        "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {

            System.out.println(
                    "Enter a valid ID, name and email address."
            );
            return;
        }

        Person person;

        switch (role) {
            case "Judge":
                person = new Judge(
                        personId, fullName, email
                );
                break;

            case "Teacher":
                person = new Teacher(
                        personId, fullName, email
                );
                break;

            default:
                person = new Participant(
                        personId, fullName, email
                );
                break;
        }

        boolean added = service.addRecipient(person);

        if (added) {
            System.out.println(
                    role + " added successfully."
            );
        } else {
            System.out.println(
                    "A person with this ID already exists."
            );
        }
    }

    private static void viewSeparateLists(
            Scanner sc, InvitationService service) {

        System.out.println("\n1. Students / Participants");
        System.out.println("2. Teachers");
        System.out.println("3. Judges");
        System.out.println("4. Return");
        System.out.print("Choose a list: ");

        switch (sc.nextLine().trim()) {
            case "1":
                service.displayPeopleByRole("Participant");
                break;

            case "2":
                service.displayPeopleByRole("Teacher");
                break;

            case "3":
                service.displayPeopleByRole("Judge");
                break;

            case "4":
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void viewSeparateInvitations(
            Scanner sc, InvitationService service, Event event) {

        System.out.println(
                "\n1. Student / Participant Invitations"
        );
        System.out.println("2. Teacher Invitations");
        System.out.println("3. Judge Invitations");
        System.out.println("4. Return");
        System.out.print("Choose a category: ");

        switch (sc.nextLine().trim()) {
            case "1":
                service.displayInvitationsByRole(
                        event, "Participant"
                );
                break;

            case "2":
                service.displayInvitationsByRole(
                        event, "Teacher"
                );
                break;

            case "3":
                service.displayInvitationsByRole(
                        event, "Judge"
                );
                break;

            case "4":
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void showAttendanceReport(Event event) {

        System.out.println("\n==================================");
        System.out.println("       ATTENDANCE REPORT");
        System.out.println("==================================");

        if (event.getAttendees().isEmpty()) {
            System.out.println(
                    "No attendees have been registered."
            );
            return;
        }

        int present = 0;
        int notInside = 0;

        System.out.printf(
                "%-15s %-25s %-15s%n",
                "Attendee ID", "Name", "Attendance"
        );

        System.out.println(
                "-------------------------------------------------------"
        );

        for (Attendee attendee : event.getAttendees()) {

            String status;

            if (attendee.isInsideVenue()) {
                status = "PRESENT";
                present++;
            } else {
                status = "NOT INSIDE";
                notInside++;
            }

            System.out.printf(
                    "%-15s %-25s %-15s%n",
                    attendee.getUserId(),
                    attendee.getName(),
                    status
            );
        }

        System.out.println(
                "-------------------------------------------------------"
        );

        System.out.println(
                "Total registered attendees: "
                        + event.getAttendees().size()
        );

        System.out.println("Currently inside venue: " + present);
        System.out.println("Not currently inside: " + notInside);
        System.out.println("==================================");

        System.out.println(
                "PRESENT means currently inside the venue."
        );
    }
}
