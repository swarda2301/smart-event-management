package eventmanagement;

public class StaffPass extends Pass {
    public StaffPass(String passId, String attendeeId) {
        super(passId, attendeeId);
    }

    @Override
    public String getAccessLevel() {
        return "GENERAL + VIP + STAFF";
    }

    @Override
    public boolean checkAccess(String zone) {
        return true;
    }
}
