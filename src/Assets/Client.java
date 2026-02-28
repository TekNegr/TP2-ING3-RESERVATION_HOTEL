package Assets;

import java.time.LocalDate;

public class Client extends Personne {
    private final LocalDate registrationDate;
    private int loyaltyPoints;

    public Client(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.registrationDate = LocalDate.now();
        this.loyaltyPoints = 0;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            loyaltyPoints += points;
        }
    }

    public double getDiscountRate() {
        return 0.0;
    }

    public String getClientType() {
        return "CLIENT";
    }
}
