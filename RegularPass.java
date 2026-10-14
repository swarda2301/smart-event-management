package eventmanagement;

public class RegularPass extends Pass {
    public RegularPass(String passId, String attendeeId) {
        super(passId, attendeeId);
    }

    @Override
    public String getAccessLevel() {
        return "GENERAL";
    }

    @Override
    public boolean checkAccess(String zone) {
        return "GENERAL".equalsIgnoreCase(zone);
    }
}
