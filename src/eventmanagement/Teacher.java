
package eventmanagement;

public class Teacher extends Person {

    public Teacher(String personId, String fullName,
                   String email) {
        super(personId, fullName, email, "Teacher");
    }

    @Override
    public String getInvitationMessage(Event event) {
        return "Respected " + getFullName() + ",\n\n"
                + "We cordially invite you to attend "
                + event.getEventName() + ".\n\n"
                + "Event Type: " + event.getEventType() + "\n"
                + "Date: " + event.getDate() + "\n"
                + "Time: " + event.getTime() + "\n"
                + "Venue: " + event.getVenue() + "\n\n"
                + "Your presence, encouragement, and guidance "
                + "would be greatly appreciated.\n\n"
                + "We look forward to welcoming you.\n\n"
                + "Regards,\n" + event.getOrganizer();
    }
}
