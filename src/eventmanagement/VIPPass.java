
package eventmanagement;

public class VIPPass extends Pass {

    public VIPPass(String passId, String attendeeId) {
        super(passId, "VIP");
    }

    @Override
    public String getAccessLevel() {
        return "GENERAL + VIP";
    }

    @Override
    public boolean checkAccess(String zone) {
        return "GENERAL".equalsIgnoreCase(zone)
                || "VIP".equalsIgnoreCase(zone);
    }
}
