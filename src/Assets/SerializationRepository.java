package Assets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class SerializationRepository {
    public void serializeToFile(Serializable data, String path) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("Data to serialize is required.");
        }
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("File path is required.");
        }

        Path outputPath = Path.of(path.trim());
        if (outputPath.getParent() != null) {
            Files.createDirectories(outputPath.getParent());
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(outputPath))) {
            objectOutputStream.writeObject(data);
        }
    }

    public Object deserializeFromFile(String path) throws IOException, ClassNotFoundException {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("File path is required.");
        }

        Path inputPath = Path.of(path.trim());
        if (!Files.exists(inputPath)) {
            throw new IllegalArgumentException("File not found: " + inputPath);
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(inputPath))) {
            return objectInputStream.readObject();
        }
    }
}
