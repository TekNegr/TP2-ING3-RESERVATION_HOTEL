import java.util.Date;
import java.util.Dictionary;

public class Housing {
    private String name;
    private String adress;
    private HousingType type;
    private int maxCapacity;
    private double pricePerNight;
    private String description;
    private Reservation[] reservations;
    private Dictionary<Date,Boolean> availabilityCalendar;
    private Amenity[] amenities;
    private Rating[] ratings;
    private double avgRating;

    public Housing(String name, String adress, 
            HousingType type, int maxCapacity, 
            double pricePerNight,String description,
            Reservation[] reservations, Amenity[] amenities, Rating[] ratings)
    {
        this.name = name;
        this.adress = adress;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.reservations = reservations;
        this.amenities = amenities;
        this.ratings = ratings;
        for (Reservation reservation : reservations) {
            Date start = reservation.reservationDates()[0];
            Date end = reservation.reservationDates()[1];
            for (Date date = start; date.before(end) || date.equals(end); date.setDate(date.getDate() + 1)) {
                availabilityCalendar.put(date, false);
            }
        }
    }

    // Getter Setters 

    // Getters

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
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

    public Reservation[] getReservations() {
        return reservations;
    }

    public Dictionary<Date, Boolean> getAvailabilityCalendar() {
        return availabilityCalendar;
    }

    public Amenity[] getAmenities() {
        return amenities;
    }

    public Rating[] getRatings() {
        return ratings;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public double calculateAvgRating() {
        double total = 0;
        for (Rating rating : ratings) {
            total += rating.getScore();
        }
        return total / ratings.length;
    }

    public double calculateStayPrice(Date startDate, Date endDate) {
        long diffInMillies = endDate.getTime() - startDate.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return diffInDays * pricePerNight;
    }

    public boolean isAvailable(Date startDate, Date endDate) {
        for (Date date = startDate; date.before(endDate) || date.equals(endDate); date.setDate(date.getDate() + 1)) {
            if (availabilityCalendar.get(date) != null && !availabilityCalendar.get(date)) {
                return false;
            }
        }
        return true;
    }

    // Get All the housing's details
    public String get(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Adress: ").append(adress).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Max Capacity: ").append(maxCapacity).append("\n");
        sb.append("Price Per Night: ").append(pricePerNight).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Average Rating: ").append(avgRating).append("\n");
        sb.append("Amenities: ").append("\n");
        for (Amenity amenity : amenities) {
            sb.append("- ").append(amenity.getName()).append(": ").append(amenity.getDescription()).append("\n");
        }
        return sb.toString();
    }
// Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public void setReservations(Reservation[] reservations) {
        this.reservations = reservations;
    }

    public void setAvailabilityOnRange(Date startDate, Date endDate, boolean isAvailable) {
        for (Date date = startDate; date.before(endDate) || date.equals(endDate); date.setDate(date.getDate() + 1)) {
            availabilityCalendar.put(date, isAvailable);
        }
        // check if there are reservations that conflict with the new availability and remove them if necessary
        for (Reservation reservation : reservations) {
            Date resStart = reservation.reservationDates()[0];
            Date resEnd = reservation.reservationDates()[1];
            if ((resStart.after(startDate) && resStart.before(endDate)) || (resEnd.after(startDate) && resEnd.before(endDate)) || (resStart.equals(startDate) || resEnd.equals(endDate))) {
                // remove reservation
                Reservation[] newReservations = new Reservation[reservations.length - 1];
                int index = 0;
                for (Reservation r : reservations) {
                    if (r != reservation) {
                        newReservations[index++] = r;
                    }
                }
                setReservations(newReservations);
            }
        }
    }

    public void setAvailabilityCalendar(Dictionary<Date, Boolean> availabilityCalendar) {
        this.availabilityCalendar = availabilityCalendar;
    }

    public void setAmenities(Amenity[] amenities) {
        this.amenities = amenities;
    }

    public void setRatings(Rating[] ratings) {
        this.ratings = ratings;
        double total = 0;
        for (Rating rating : ratings) {
            total += rating.getScore();
        }
        this.avgRating = calculateAvgRating();
    }

    public void addRating(Rating rating) {
        Rating[] newRatings = new Rating[ratings.length + 1];
        System.arraycopy(ratings, 0, newRatings, 0, ratings.length);
        newRatings[ratings.length] = rating;
        setRatings(newRatings);
    }
}
