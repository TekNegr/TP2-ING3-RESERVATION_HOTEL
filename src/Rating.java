import java.util.Date;

public class Rating {
    private User user;
    private Housing housing;
    private int score;
    private String comment;
    private Date publishDate;

    public Rating(User user, Housing housing, int score, String comment, Date date) {
        this.user = user;
        this.housing = housing;
        this.score = score;
        this.comment = comment;
        this.publishDate = date;
    }

    public User getUser() {
        return user;
    }

    public Housing getHousing() {
        return housing;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHousing(Housing housing) {
        this.housing = housing;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

}
