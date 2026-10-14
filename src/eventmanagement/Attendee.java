package eventmanagement;

public abstract class Attendee extends User implements EntryValidatable {
    private Pass pass;
    private boolean insideVenue;

    public Attendee(String userId, String name,
                    String contactNumber, Pass pass) {
        super(userId, name, contactNumber);
        this.pass = pass;
        this.insideVenue = false;
    }

    public Pass getPass() {
        return pass;
    }

    public boolean isInsideVenue() {
        return insideVenue;
    }

    public void setInsideVenue(boolean insideVenue) {
        this.insideVenue = insideVenue;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    @Override
    public boolean validateEntry() {
        return pass != null
                && pass.validatePass()
                && !insideVenue;
    }

    @Override
    public void displayDetails() {
        System.out.println("ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Contact: " + getContactNumber());
    }
}