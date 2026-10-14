
package eventmanagement;

public class RegularAttendee extends Attendee {

    public RegularAttendee(String userId, String name, String contactNumber) {
        super(userId, name, contactNumber,
                new RegularPass("PASS-" + userId, userId));
    }

    @Override
    public void displayDetails() {
        System.out.println("[REGULAR] " + getName()
                + " | ID: " + getUserId()
                + " | Pass: " + (getPass() == null
                ? "Not issued" : getPass().getPassId()));
    }
}
