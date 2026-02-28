package Assets;

public class AncienClient extends Client {
    private int reservationCount;

    public AncienClient(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.reservationCount = 0;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public void recordReservation() {
        reservationCount++;
        addLoyaltyPoints(10);
    }

    @Override
    public double getDiscountRate() {
        if (reservationCount >= 10) return 0.15;
        if (reservationCount >= 5) return 0.10;
        if (reservationCount >= 3) return 0.05;
        return 0.0;
    }

    @Override
    public String getClientType() {
        return "ANCIEN_CLIENT";
    }
}
