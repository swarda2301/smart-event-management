package eventmanagement;

public abstract class Pass implements AccessControllable {
    private final String passId;
    private final String attendeeId;
    private boolean valid;
    private boolean used;

    public Pass(String passId, String attendeeId) {
        this.passId = passId;
        this.attendeeId = attendeeId;
        this.valid = true;
        this.used = false;
    }

    public String getPassId() {
        return passId;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isUsed() {
        return used;
    }

    public void invalidate() {
        valid = false;
    }

    public void markAsUsed() {
        used = true;
    }

    public boolean validatePass() {
        return valid && !used;
    }

    public abstract String getAccessLevel();
}
