
package eventmanagement;

public class Staff extends Attendee {

    public Staff(String userId, String name, String contactNumber) {
        super(userId, name, contactNumber,
                new StaffPass("PASS-" + userId, userId));
    }

    @Override
    public void displayDetails() {
        System.out.println("[STAFF] " + getName()
                + " | ID: " + getUserId()
                + " | Pass: " + getPass().getPassId());
    }
}
