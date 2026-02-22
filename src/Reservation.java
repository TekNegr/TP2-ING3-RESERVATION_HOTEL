import java.util.Date;

public class Reservation {
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

}
