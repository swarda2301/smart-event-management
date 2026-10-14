
package eventmanagement;

public class Judge extends Person {

    public Judge(String personId, String fullName,
                 String email) {
        super(personId, fullName, email, "Judge");
    }

    @Override
    public String getInvitationMessage(Event event) {
        return "Respected " + getFullName() + ",\n\n"
                + "We are pleased to invite you to serve "
                + "as a judge for "
                + event.getEventName() + ".\n\n"
                + "Event Type: " + event.getEventType() + "\n"
                + "Date: " + event.getDate() + "\n"
                + "Time: " + event.getTime() + "\n"
                + "Venue: " + event.getVenue() + "\n\n"
                + "Your expertise and valuable evaluation "
                + "would greatly contribute to the success "
                + "of this event.\n\n"
                + "We would be honoured by your presence.\n\n"
                + "Regards,\n" + event.getOrganizer();
    }
}
