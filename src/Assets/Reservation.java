package Assets;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private Client client;
    private Housing housing;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private final LocalDateTime bookingDate;
    private double discountRate;
    private double totalPrice;

    public Reservation(Client client, Housing housing, LocalDate startDate, LocalDate endDate, int numberOfGuests) {
        this.client = client;
        this.housing = housing;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.status = ReservationStatus.PENDING;
        this.bookingDate = LocalDateTime.now();
        this.discountRate = 0.0;
        this.totalPrice = calculateTotalPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public Housing getHousing() {
        return housing;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void updateReservation(LocalDate startDate, LocalDate endDate, int numberOfGuests) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice();
    }

    public double calculateTotalPrice() {
        double base = housing.calculateStayPrice(startDate, endDate);
        return base * (1.0 - discountRate);
    }

    public void applyDiscount(double rate) {
        if (rate < 0) rate = 0;
        if (rate > 1) rate = 1;
        discountRate = rate;
        totalPrice = calculateTotalPrice();
    }

    public void confirm() {
        status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        status = ReservationStatus.CANCELLED;
    }

    public boolean isConfirmedAndActive(LocalDate today) {
        return status == ReservationStatus.CONFIRMED
                && (today.isEqual(startDate) || today.isAfter(startDate))
                && today.isBefore(endDate);
    }

    public String getDetails() {
        return "Reservation #" + id
                + " | client=" + client.getEmail()
                + " | housing=" + housing.getName()
                + " | from=" + startDate
                + " | to=" + endDate
                + " | guests=" + numberOfGuests
                + " | status=" + status
                + " | discount=" + (int) (discountRate * 100) + "%"
                + " | total=" + String.format("%.2f", totalPrice) + "€";
    }
}
