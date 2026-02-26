import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
    private int id;
    private User user;
    private Housing housing;
    private Date startDate;
    private Date endDate;
    private int numberOfGuests;

    public Reservation(User user, Housing housing, Date startDate, Date endDate, int numberOfGuests) {
        this.user = user;
        this.housing = housing;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Housing getHousing(){
        return housing;
    }

    public Date[] reservationDates(){
        return new Date[]{startDate, endDate};
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public String getDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation ID: ").append(id).append("\n");
        sb.append("User: ").append(user != null ? user.getEmail() : "(unknown)").append("\n");
        sb.append("Housing: ").append(housing != null ? housing.getName() : "(unknown)").append("\n");
        sb.append("Address: ").append(housing != null ? housing.getAdress() : "(unknown)").append("\n");
        sb.append("From: ").append(sdf.format(startDate)).append(" To: ").append(sdf.format(endDate)).append("\n");
        sb.append("Guests: ").append(numberOfGuests).append("\n");
        return sb.toString();
    }

}
