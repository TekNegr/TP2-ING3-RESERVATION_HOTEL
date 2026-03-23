package Assets;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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
        validateReservationInput(client, housing, startDate, endDate, numberOfGuests);
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

    public void setClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client is required.");
        }
        this.client = client;
    }

    public Housing getHousing() {
        return housing;
    }

    public void setHousing(Housing housing) {
        if (housing == null) {
            throw new IllegalArgumentException("Housing is required.");
        }
        this.housing = housing;
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
        validateReservationInput(client, housing, startDate, endDate, numberOfGuests);
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = calculateTotalPrice();
    }

    public double calculateTotalPrice() {
        if (housing == null) {
            throw new IllegalStateException("Reservation has no housing.");
        }
        double base = housing.calculateStayPrice(startDate, endDate);
        return base * (1.0 - discountRate);
    }

    public void applyDiscount(double rate) {
        if (rate < 0 || rate > 1) {
            throw new IllegalArgumentException("Discount rate must be between 0.0 and 1.0.");
        }
        discountRate = rate;
        totalPrice = calculateTotalPrice();
    }

    public void confirm() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled reservation cannot be confirmed.");
        }
        if (status == ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation is already confirmed.");
        }
        status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
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

    private void validateReservationInput(
            Client client,
            Housing housing,
            LocalDate startDate,
            LocalDate endDate,
            int numberOfGuests
    ) {
        if (client == null) {
            throw new IllegalArgumentException("Client is required.");
        }
        if (housing == null) {
            throw new IllegalArgumentException("Housing is required.");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates are required.");
        }
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (numberOfGuests <= 0) {
            throw new IllegalArgumentException("Number of guests must be greater than 0.");
        }
        if (numberOfGuests > housing.getMaxCapacity()) {
            throw new IllegalArgumentException("Number of guests exceeds housing capacity.");
        }
    }
}
