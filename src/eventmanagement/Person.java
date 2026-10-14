
package eventmanagement;

public abstract class Person {

    private String personId;
    private String fullName;
    private String email;
    private String role;

    public Person(String personId, String fullName,
                  String email, String role) {
        this.personId = personId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getInvitationMessage(Event event);
}
