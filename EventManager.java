package eventmanagement;

public class EventManager {
    private final Event event;
    private final QueueManager queueManager;
    private final CrowdManager crowdManager;

    public EventManager(Event event) {
        this.event = event;
        this.queueManager = new QueueManager();
        this.crowdManager = new CrowdManager();
    }

    public void addAttendeeToQueue(String attendeeId) {
        Attendee attendee = event.findAttendee(attendeeId);

        if (attendee == null) {
            System.out.println("Attendee not found.");
            return;
        }

        if (attendee.getPass() == null) {
            System.out.println("No pass has been issued to this attendee.");
            return;
        }

        if (attendee.isInsideVenue()) {
            System.out.println("Attendee is already inside the venue.");
            return;
        }

        queueManager.addToQueue(attendee);
        System.out.println(attendee.getName() + " added to the waiting queue.");
    }

    public void processNextEntry(String gateId) {
        if (queueManager.isEmpty()) {
            System.out.println("No attendees are waiting in the queue.");
            return;
        }

        EntryGate gate = event.findGate(gateId);
        if (gate == null) {
            System.out.println("Gate not found.");
            return;
        }

        Attendee attendee = queueManager.getNextAttendee();

        if (event.getVenue().isFull()) {
            System.out.println("ENTRY DENIED: Venue capacity reached.");
            crowdManager.displayCrowdStatus(event.getVenue());
            return;
        }

        if (!attendee.validateEntry()) {
            queueManager.removeFromQueue();
            System.out.println("ENTRY DENIED: Invalid or already-used pass.");
            return;
        }

        if (!gate.isAvailable()) {
            System.out.println("Gate is full. Choose another gate.");
            return;
        }

        queueManager.removeFromQueue();
        gate.allowEntry();
        event.getVenue().increaseOccupancy();
        attendee.getPass().markAsUsed();
        attendee.setInsideVenue(true);

        String recordId = "REC" + (event.getRecords().size() + 1);
        EntryRecord record = new EntryRecord(
                recordId,
                attendee.getUserId(),
                gate.getGateId()
        );
        event.getRecords().add(record);

        System.out.println("ENTRY ALLOWED");
        System.out.println("Attendee : " + attendee.getName());
        System.out.println("Gate     : " + gate.getGateName());
        System.out.println("Access   : " + attendee.getPass().getAccessLevel());

        crowdManager.displayCrowdStatus(event.getVenue());
    }

    public void processExit(String attendeeId) {
        Attendee attendee = event.findAttendee(attendeeId);

        if (attendee == null) {
            System.out.println("Attendee not found.");
            return;
        }

        if (!attendee.isInsideVenue()) {
            System.out.println("Attendee is not currently inside.");
            return;
        }

        EntryRecord record = null;
        for (EntryRecord r : event.getRecords()) {
            if (r.getAttendeeId().equalsIgnoreCase(attendeeId)
                    && "INSIDE".equals(r.getStatus())) {
                record = r;
                break;
            }
        }

        if (record != null) {
            EntryGate gate = event.findGate(record.getGateId());
            if (gate != null) {
                gate.exitAttendee();
            }
            record.markExit();
        }

        attendee.setInsideVenue(false);
        event.getVenue().decreaseOccupancy();

        System.out.println(attendee.getName() + " has exited the venue.");
        crowdManager.displayCrowdStatus(event.getVenue());
    }

    public void showQueue() {
        queueManager.displayQueue();
    }

    public void showCrowdStatus() {
        crowdManager.displayCrowdStatus(event.getVenue());
    }

    public void showGates() {
        System.out.println("\n--- Gate Status ---");
        for (EntryGate gate : event.getGates()) {
            gate.displayStatus();
        }
    }

    public void showRecords() {
        System.out.println("\n--- Entry / Exit Records ---");
        if (event.getRecords().isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (EntryRecord record : event.getRecords()) {
            record.displayRecord();
        }
    }
}
