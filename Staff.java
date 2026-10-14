package eventmanagement;

public class Staff extends User implements AccessControllable {
    private final String role;

    public Staff(String userId, String name, String contactNumber, String role) {
        super(userId, name, contactNumber);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean checkAccess(String zone) {
        return true;
    }

    @Override
    public void displayDetails() {
        System.out.println("[STAFF] " + getName()
                + " | Role: " + role
                + " | ID: " + getUserId());
    }
}
