package eventmanagement;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Venue venue = new Venue("V001", "PCCOE Main Auditorium", 10);

        Event event = new Event(
                "E001",
                "PRAXIS 2026",
                "10-10-2026",
                venue
        );

        event.addGate(new EntryGate("G1", "Gate A", 5));
        event.addGate(new EntryGate("G2", "Gate B", 5));

        EventManager manager = new EventManager(event);

        loadDemoAttendees(event);

        boolean running = true;

        System.out.println("\n==============================================");
        System.out.println(" EVENT CROWD, QUEUE AND ACCESS CONTROL SYSTEM");
        System.out.println("==============================================");

        while (running) {
            displayMenu();
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> event.displayEventDetails();
                    case 2 -> displayAttendees(event);
                    case 3 -> addAttendeeToQueue(manager);
                    case 4 -> processEntry(manager);
                    case 5 -> processExit(manager);
                    case 6 -> manager.showQueue();
                    case 7 -> manager.showGates();
                    case 8 -> manager.showCrowdStatus();
                    case 9 -> manager.showRecords();
                    case 10 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        System.out.println("Program ended. Thank you.");
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n------------- MENU -------------");
        System.out.println("1. Display Event Details");
        System.out.println("2. Display Attendees");
        System.out.println("3. Add Attendee to Entry Queue");
        System.out.println("4. Process Entry");
        System.out.println("5. Process Exit");
        System.out.println("6. Display Waiting Queue");
        System.out.println("7. Display Gate Status");
        System.out.println("8. Display Crowd Status");
        System.out.println("9. Display Entry/Exit Records");
        System.out.println("10. Exit");
        System.out.println("--------------------------------");
    }

    private static void loadDemoAttendees(Event event) {
        RegularAttendee a1 =
                new RegularAttendee("A101", "Swarda", "9876543210");
        a1.setPass(new RegularPass("P101", "A101"));

        VIPAttendee a2 =
                new VIPAttendee("A102", "Sharvari", "9876543211");
        a2.setPass(new VIPPass("P102", "A102"));

        RegularAttendee a3 =
                new RegularAttendee("A103", "Samruddhi", "9876543212");
        a3.setPass(new RegularPass("P103", "A103"));

        event.addAttendee(a1);
        event.addAttendee(a2);
        event.addAttendee(a3);
    }

    private static void displayAttendees(Event event) {
        System.out.println("\n--- Attendees ---");
        for (Attendee attendee : event.getAttendees()) {
            attendee.displayDetails();
        }
    }

    private static void addAttendeeToQueue(EventManager manager) {
        String id = readString("Enter attendee ID: ");
        manager.addAttendeeToQueue(id);
    }

    private static void processEntry(EventManager manager) {
        String gateId = readString("Enter gate ID (G1/G2): ");
        manager.processNextEntry(gateId);
    }

    private static void processExit(EventManager manager) {
        String attendeeId = readString("Enter attendee ID: ");
        manager.processExit(attendeeId);
    }

    private static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
