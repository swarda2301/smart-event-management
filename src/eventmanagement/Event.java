
package eventmanagement;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private String eventId;
    private String eventName;
    private String eventType;
    private String date;
    private String time;
    private String venue;
    private String organizer;
    private String description;

    private Venue venueManager;
    private final List<Attendee> attendees;
    private final List<EntryGate> gates;
    private final List<EntryRecord> records;

    public Event(String eventId, String eventName,
                 String eventType, String date,
                 String time, String venue,
                 String organizer, String description) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.organizer = organizer;
        this.description = description;

        // Default capacity is 100; Main can change it.
        this.venueManager = new Venue("VENUE-1", venue, 100);

        this.attendees = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    // Event detail getters

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getDescription() {
        return description;
    }

    // Venue management

    public Venue getVenueManager() {
        return venueManager;
    }

    public Venue getVenueObject() {
        return venueManager;
    }

    public void setVenueManager(Venue venueManager) {
        if (venueManager == null) {
            throw new IllegalArgumentException(
                    "Venue cannot be null.");
        }

        if (venueManager.getCurrentOccupancy() > 0) {
            throw new IllegalArgumentException(
                    "Cannot replace a venue that is already occupied.");
        }

        this.venueManager = venueManager;
    }

    // Attendee management

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void addAttendee(Attendee attendee) {
        if (attendee == null) {
            throw new IllegalArgumentException(
                    "Attendee cannot be null.");
        }

        if (findAttendee(attendee.getUserId()) != null) {
            System.out.println("An attendee with this ID already exists.");
            return;
        }

        attendees.add(attendee);
    }

    public Attendee findAttendee(String attendeeId) {
        for (Attendee attendee : attendees) {
            if (attendee.getUserId().equalsIgnoreCase(attendeeId)) {
                return attendee;
            }
        }

        return null;
    }

    // Entry gate management

    public List<EntryGate> getGates() {
        return gates;
    }

    public void addGate(EntryGate gate) {
        if (gate == null) {
            throw new IllegalArgumentException(
                    "Gate cannot be null.");
        }

        if (findGate(gate.getGateId()) != null) {
            System.out.println("A gate with this ID already exists.");
            return;
        }

        gates.add(gate);
    }

    public EntryGate findGate(String gateId) {
        for (EntryGate gate : gates) {
            if (gate.getGateId().equalsIgnoreCase(gateId)) {
                return gate;
            }
        }

        return null;
    }

    // Entry and exit records

    public List<EntryRecord> getRecords() {
        return records;
    }

    // Display event details

    public void displayEventDetails() {
        System.out.println("\n===== EVENT DETAILS =====");
        System.out.println("Event ID: " + eventId);
        System.out.println("Event Name: " + eventName);
        System.out.println("Event Type: " + eventType);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Venue: " + venue);
        System.out.println("Organizer: " + organizer);
        System.out.println("Description: " + description);
        System.out.println("Venue Capacity: "
                + venueManager.getMaximumCapacity());
        System.out.println("Current Occupancy: "
                + venueManager.getCurrentOccupancy());
    }
}
