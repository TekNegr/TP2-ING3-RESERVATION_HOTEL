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

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("--- Loyalty & Discount ---");
        System.out.println("  Reservations made: " + reservationCount);
        double rate = getDiscountRate();
        if (rate > 0) {
            System.out.println("  Discount rate    : " + (int)(rate * 100) + "%");
        } else {
            System.out.println("  Discount rate    : none (need 3+ reservations)");
        }
        if (reservationCount < 3) {
            System.out.println("  Next threshold   : " + (3 - reservationCount) + " more to get 5% off");
        } else if (reservationCount < 5) {
            System.out.println("  Next threshold   : " + (5 - reservationCount) + " more to get 10% off");
        } else if (reservationCount < 10) {
            System.out.println("  Next threshold   : " + (10 - reservationCount) + " more to get 15% off");
        } else {
            System.out.println("  Max discount reached!");
        }
    }
}
