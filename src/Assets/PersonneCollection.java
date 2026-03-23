package Assets;

import java.util.List;

public interface PersonneCollection {
    void ajouter(Personne personne);

    List<Personne> getAll();
}
