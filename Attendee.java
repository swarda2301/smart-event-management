package eventmanagement;

public abstract class Attendee extends User implements EntryValidatable {
    private Pass pass;
    private boolean insideVenue;

    public Attendee(String userId, String name, String contactNumber) {
        super(userId, name, contactNumber);
        this.insideVenue = false;
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public boolean isInsideVenue() {
        return insideVenue;
    }

    public void setInsideVenue(boolean insideVenue) {
        this.insideVenue = insideVenue;
    }

    @Override
    public boolean validateEntry() {
        return pass != null && pass.validatePass() && !insideVenue;
    }

    @Override
    public void displayDetails() {
        System.out.println("ID: " + getUserId()
                + " | Name: " + getName()
                + " | Contact: " + getContactNumber());
    }
}
