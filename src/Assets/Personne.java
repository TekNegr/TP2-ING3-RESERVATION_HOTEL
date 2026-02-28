package Assets;

import java.time.LocalDateTime;

public abstract class Personne {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private final LocalDateTime createdAt;

    protected Personne(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean verifyPassword(String inputPassword) {
        return password != null && password.equals(inputPassword);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
