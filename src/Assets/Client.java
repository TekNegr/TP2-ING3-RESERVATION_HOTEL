package Assets;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {
    private final LocalDate registrationDate;
    private int loyaltyPoints;
    private final List<Housing> ownedHousings = new ArrayList<>();
    private final List<Reservation> myReservations = new ArrayList<>();

    public Client() {
        this("Default", "Client", "default.client@example.com", "password");
    }

    public Client(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.registrationDate = LocalDate.now();
        this.loyaltyPoints = 0;
    }

    public Client(Client other) {
        super(other);
        this.registrationDate = other.registrationDate;
        this.loyaltyPoints = other.loyaltyPoints;
        this.ownedHousings.addAll(other.ownedHousings);
        this.myReservations.addAll(other.myReservations);
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

    public void addOwnedHousing(Housing housing) {
        if (housing != null) ownedHousings.add(housing);
    }

    public void removeOwnedHousing(Housing housing) {
        ownedHousings.remove(housing);
    }

    public List<Housing> getOwnedHousings() {
        return ownedHousings;
    }

    public void addMyReservation(Reservation reservation) {
        if (reservation != null) myReservations.add(reservation);
    }

    public List<Reservation> getMyReservations() {
        return myReservations;
    }

    @Override
    public void afficher() {
        System.out.println("=== Client Details ===");
        System.out.println("Name        : " + getFullName());
        System.out.println("Email       : " + getEmail());
        System.out.println("Type        : " + getClientType());
        System.out.println("Registered  : " + registrationDate);
        System.out.println("Member since: " + getCreatedAt().toLocalDate());
        System.out.println("Loyalty pts : " + loyaltyPoints);
        System.out.println("--- Owned Properties (" + ownedHousings.size() + ") ---");
        if (ownedHousings.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Housing h : ownedHousings) {
                System.out.println("  " + h.getSummary());
            }
        }
        System.out.println("--- My Reservations (" + myReservations.size() + ") ---");
        if (myReservations.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Reservation r : myReservations) {
                System.out.println("  " + r.getDetails());
            }
        }
    }

    @Override
    public String toString() {
        return "Client{" + super.toString()
            + ", type='" + getClientType() + "', loyaltyPoints=" + loyaltyPoints
            + ", registrationDate=" + registrationDate + "}";
    }
}
