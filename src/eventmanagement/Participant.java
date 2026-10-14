
package eventmanagement;

public class Participant extends Person {

    public Participant(String personId, String fullName,
                       String email) {
        super(personId, fullName, email, "Participant");
    }

    @Override
    public String getInvitationMessage(Event event) {
        return "Dear " + getFullName() + ",\n\n"
                + "You are invited to participate in "
                + event.getEventName() + ".\n\n"
                + "Event Type: " + event.getEventType() + "\n"
                + "Date: " + event.getDate() + "\n"
                + "Time: " + event.getTime() + "\n"
                + "Venue: " + event.getVenue() + "\n\n"
                + event.getDescription() + "\n\n"
                + "We look forward to your participation!\n\n"
                + "Regards,\n" + event.getOrganizer();
    }
}
