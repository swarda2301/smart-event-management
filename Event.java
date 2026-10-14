package eventmanagement;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String eventId;
    private final String eventName;
    private final String date;
    private final Venue venue;
    private final List<Attendee> attendees;
    private final List<EntryGate> gates;
    private final List<EntryRecord> records;

    public Event(String eventId, String eventName, String date, Venue venue) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.date = date;
        this.venue = venue;
        this.attendees = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public List<EntryGate> getGates() {
        return gates;
    }

    public List<EntryRecord> getRecords() {
        return records;
    }

    public void addAttendee(Attendee attendee) {
        if (attendee == null) {
            throw new IllegalArgumentException("Attendee cannot be null.");
        }
        attendees.add(attendee);
    }

    public void addGate(EntryGate gate) {
        if (gate == null) {
            throw new IllegalArgumentException("Gate cannot be null.");
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

    public Attendee findAttendee(String attendeeId) {
        for (Attendee attendee : attendees) {
            if (attendee.getUserId().equalsIgnoreCase(attendeeId)) {
                return attendee;
            }
        }
        return null;
    }

    public void displayEventDetails() {
        System.out.println("\n======================================");
        System.out.println("EVENT: " + eventName);
        System.out.println("ID   : " + eventId);
        System.out.println("DATE : " + date);
        System.out.println("VENUE: " + venue.getVenueName());
        System.out.println("======================================");
    }
}
