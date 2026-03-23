package Assets;

import java.util.ArrayList;
import java.util.List;

public class PersonneCollectionImpl implements PersonneCollection {
    private final List<Personne> personnes = new ArrayList<>();

    @Override
    public void ajouter(Personne personne) {
        if (personne != null) {
            personnes.add(personne);
        }
    }

    @Override
    public List<Personne> getAll() {
        return new ArrayList<>(personnes);
    }
}
