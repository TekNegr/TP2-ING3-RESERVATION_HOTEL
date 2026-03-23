package Assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HousingTextRepository {
    public static class HousingRecord {
        private final String name;
        private final String address;
        private final String type;
        private final int maxCapacity;
        private final double pricePerNight;
        private final String description;
        private final String ownerEmail;
        private final List<String> comments;
        private final List<Amenity> amenities;

        public HousingRecord(
                String name,
                String address,
                String type,
                int maxCapacity,
                double pricePerNight,
                String description,
                String ownerEmail,
                List<String> comments,
                List<Amenity> amenities
        ) {
            this.name = name;
            this.address = address;
            this.type = type;
            this.maxCapacity = maxCapacity;
            this.pricePerNight = pricePerNight;
            this.description = description;
            this.ownerEmail = ownerEmail;
            this.comments = comments;
            this.amenities = amenities;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
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

        public String getOwnerEmail() {
            return ownerEmail;
        }

        public List<String> getComments() {
            return comments;
        }

        public List<Amenity> getAmenities() {
            return amenities;
        }
    }

    public void saveToTextFile(List<Housing> housings, String path) throws IOException {
        if (housings == null) {
            throw new IllegalArgumentException("Housings list is required.");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("File path is required.");
        }

        Path outputPath = Path.of(path.trim());
        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            for (Housing housing : housings) {
                writer.write(toLine(housing));
                writer.newLine();
            }
        }
    }

    public List<HousingRecord> loadFromTextFile(String path) throws IOException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("File path is required.");
        }

        Path inputPath = Path.of(path.trim());
        if (!Files.exists(inputPath)) {
            throw new IllegalArgumentException("File not found: " + inputPath);
        }

        List<HousingRecord> records = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                records.add(parseLine(line, lineNumber));
            }
        }
        return records;
    }

    private String toLine(Housing housing) {
        if (housing == null) {
            throw new IllegalArgumentException("Housing cannot be null.");
        }

        String ownerEmail = housing.getOwner() == null ? "" : sanitizeField(housing.getOwner().getEmail());

        List<String> comments = new ArrayList<>();
        for (Rating rating : housing.getRatings()) {
            String comment = rating.getComment();
            if (comment != null && !comment.isBlank()) {
                comments.add(sanitizeListItem(comment));
            }
        }

        List<String> amenityItems = new ArrayList<>();
        for (Amenity amenity : housing.getAmenities()) {
            String item = sanitizeListItem(amenity.getName()) + "::" + sanitizeListItem(amenity.getDescription());
            amenityItems.add(item);
        }

        return String.join(";",
                sanitizeField(housing.getName()),
                sanitizeField(housing.getAddress()),
                sanitizeField(housing.getType().name()),
                Integer.toString(housing.getMaxCapacity()),
                Double.toString(housing.getPricePerNight()),
                sanitizeField(housing.getDescription()),
                ownerEmail,
                String.join("|", comments),
                String.join("|", amenityItems)
        );
    }

    private HousingRecord parseLine(String line, int lineNumber) {
        String[] fields = line.split(";", -1);
        if (fields.length < 9) {
            throw new IllegalArgumentException("Invalid housing line at " + lineNumber + ": expected 9 ';' separated fields.");
        }

        String name = fields[0].trim();
        String address = fields[1].trim();
        String type = fields[2].trim();

        int maxCapacity;
        double pricePerNight;
        try {
            maxCapacity = Integer.parseInt(fields[3].trim());
            pricePerNight = Double.parseDouble(fields[4].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value at line " + lineNumber + ": " + e.getMessage());
        }

        String description = fields[5].trim();
        String ownerEmail = fields[6].trim();

        List<String> comments = new ArrayList<>();
        if (!fields[7].isBlank()) {
            String[] commentTokens = fields[7].split("\\|");
            for (String token : commentTokens) {
                String comment = token.trim();
                if (!comment.isEmpty()) {
                    comments.add(comment);
                }
            }
        }

        List<Amenity> amenities = new ArrayList<>();
        if (!fields[8].isBlank()) {
            String[] amenityTokens = fields[8].split("\\|");
            for (String token : amenityTokens) {
                String[] parts = token.split("::", 2);
                String amenityName = parts[0].trim();
                if (amenityName.isEmpty()) {
                    continue;
                }
                String amenityDescription = parts.length > 1 ? parts[1].trim() : "";
                amenities.add(new Amenity(amenityName, amenityDescription));
            }
        }

        return new HousingRecord(
                name,
                address,
                type,
                maxCapacity,
                pricePerNight,
                description,
                ownerEmail,
                comments,
                amenities
        );
    }

    private String sanitizeField(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(";", ",").replace("\n", " ").replace("\r", " ").trim();
    }

    private String sanitizeListItem(String value) {
        return sanitizeField(value).replace("|", "/").replace("::", ":");
    }
}