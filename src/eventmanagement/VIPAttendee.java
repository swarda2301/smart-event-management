
package eventmanagement;

public class VIPAttendee extends Attendee {

    public VIPAttendee(String userId, String name, String contactNumber) {
        super(userId, name, contactNumber,
                new VIPPass("PASS-" + userId, userId));
    }

    @Override
    public void displayDetails() {
        System.out.println("[VIP] " + getName()
                + " | ID: " + getUserId()
                + " | Pass: " + (getPass() == null
                ? "Not issued" : getPass().getPassId()));
    }
}
