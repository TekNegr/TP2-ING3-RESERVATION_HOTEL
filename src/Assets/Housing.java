package Assets;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Housing {
    private int id;
    private String name;
    private String address;
    private HousingType type;
    private int maxCapacity;
    private double pricePerNight;
    private String description;
    private Client owner;
    private final List<Amenity> amenities;
    private final List<Rating> ratings;
    private final Set<LocalDate> unavailableDates;

    public Housing(String name, String address, HousingType type, int maxCapacity, double pricePerNight, String description, Client owner) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.owner = owner;
        this.amenities = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.unavailableDates = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public HousingType getType() {
        return type;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public String getDescription() {
        return description;
    }

    public Client getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(HousingType type) {
        this.type = type;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addAmenity(Amenity amenity) {
        if (amenity != null) {
            amenities.add(amenity);
        }
    }

    public void addRating(Rating rating) {
        if (rating != null) {
            ratings.add(rating);
        }
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getScore();
        }
        return (double) sum / ratings.size();
    }

    public double calculateStayPrice(LocalDate startDate, LocalDate endDate) {
        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        if (nights < 0) return 0.0;
        return nights * pricePerNight;
    }

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !startDate.isBefore(endDate)) {
            return false;
        }
        LocalDate cursor = startDate;
        while (cursor.isBefore(endDate)) {
            if (unavailableDates.contains(cursor)) return false;
            cursor = cursor.plusDays(1);
        }
        return true;
    }

    public void addUnavailablePeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate cursor = startDate;
        while (cursor.isBefore(endDate)) {
            unavailableDates.add(cursor);
            cursor = cursor.plusDays(1);
        }
    }

    public void removeUnavailablePeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate cursor = startDate;
        while (cursor.isBefore(endDate)) {
            unavailableDates.remove(cursor);
            cursor = cursor.plusDays(1);
        }
    }

    public String getSummary() {
        return "#" + id + " - " + name + " | " + address + " | " + type
                + " | max=" + maxCapacity + " | " + pricePerNight + "€/nuit"
                + " | note=" + String.format("%.2f", getAverageRating());
    }

    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Housing ID: ").append(id).append("\n");
        builder.append("Name: ").append(name).append("\n");
        builder.append("Address: ").append(address).append("\n");
        builder.append("Type: ").append(type).append("\n");
        builder.append("Max Capacity: ").append(maxCapacity).append("\n");
        builder.append("Price/Night: ").append(pricePerNight).append(" euro\n");
        builder.append("Description: ").append(description).append("\n");
        builder.append("Owner: ").append(owner != null ? owner.getEmail() : "(none)").append("\n");
        builder.append("Average Rating: ").append(String.format("%.2f", getAverageRating())).append("\n");
        builder.append("Ratings Count: ").append(ratings.size()).append("\n");

        if (amenities.isEmpty()) {
            builder.append("Amenities: none\n");
        } else {
            builder.append("Amenities:\n");
            for (Amenity amenity : amenities) {
                builder.append("- ").append(amenity.getName()).append(": ").append(amenity.getDescription()).append("\n");
            }
        }

        if (ratings.isEmpty()) {
            builder.append("Ratings Details: none\n");
        } else {
            builder.append("Ratings Details:\n");
            for (Rating rating : ratings) {
                builder.append(rating.getDetails()).append("\n");
            }
        }
        return builder.toString();
    }
}
