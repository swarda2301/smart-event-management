package eventmanagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EntryRecord {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final String recordId;
    private final String attendeeId;
    private final String gateId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String status;

    public EntryRecord(String recordId, String attendeeId, String gateId) {
        this.recordId = recordId;
        this.attendeeId = attendeeId;
        this.gateId = gateId;
        this.entryTime = LocalDateTime.now();
        this.status = "INSIDE";
    }

    public void markExit() {
        exitTime = LocalDateTime.now();
        status = "EXITED";
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public String getGateId() {
        return gateId;
    }

    public String getStatus() {
        return status;
    }

    public void displayRecord() {
        System.out.println("Record: " + recordId
                + " | Attendee: " + attendeeId
                + " | Gate: " + gateId
                + " | Entry: " + entryTime.format(FORMATTER)
                + " | Exit: " + (exitTime == null ? "-" : exitTime.format(FORMATTER))
                + " | Status: " + status);
    }
}
