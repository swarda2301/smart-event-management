
package eventmanagement;

public class Pass {

    private String passId;
    private String passType;
    private boolean valid;
    private boolean used;

    public Pass(String passId, String passType) {
        this.passId = passId;
        this.passType = passType;
        this.valid = true;
        this.used = false;
    }

    public String getPassId() {
        return passId;
    }

    public String getPassType() {
        return passType;
    }

    public String getAccessLevel() {
        return passType;
    }
    public boolean checkAccess(String zone) {
    return getAccessLevel().equalsIgnoreCase(zone);
}

    public boolean validatePass() {
        return valid && !used;
    }

    public void markAsUsed() {
        if (validatePass()) {
            used = true;
        } else {
            System.out.println("Pass is invalid or has already been used.");
        }
    }

    public void deactivatePass() {
        valid = false;
    }

    public void displayPassDetails() {
        System.out.println("Pass ID: " + passId);
        System.out.println("Pass Type: " + passType);
        System.out.println("Valid: " + valid);
        System.out.println("Used: " + used);
    }
}

