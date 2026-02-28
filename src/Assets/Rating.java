package Assets;

import java.time.LocalDate;

public class Rating {
    private Client client;
    private Housing housing;
    private int score;
    private String comment;
    private LocalDate publishDate;

    public Rating(Client client, Housing housing, int score, String comment, LocalDate publishDate) {
        this.client = client;
        this.housing = housing;
        this.score = Math.max(1, Math.min(5, score));
        this.comment = comment;
        this.publishDate = publishDate == null ? LocalDate.now() : publishDate;
    }

    public Client getClient() {
        return client;
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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getDetails() {
        return "- " + score + "/5 by "
                + (client != null ? client.getEmail() : "unknown")
                + " on " + publishDate
                + " | " + (comment == null || comment.isBlank() ? "(no comment)" : comment);
    }
}
