import Assets.AncienClient;
import Assets.Client;
import Assets.NouveauClient;
import Assets.Personne;
import Assets.PersonneCollection;
import Assets.PersonneCollectionImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Welcome to the HouseRental Functions Demo ===");
        System.out.println("===== Constructor demo : =====");
        runTp4ConstructorDemo();
        System.out.println("\n\n\n\n ===== Exercice 4 demo : =====");
        runTp4Ex4Demo();
    }

    private static void runTp4ConstructorDemo() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== TP4 Exercice 3.1 Demo ===");

        Client defaultClient = new Client();

        String email;
        while (true) {
            System.out.print("Enter a valid email for the parameterized client: ");
            email = scanner.nextLine().trim();
            if (!email.isEmpty() && email.contains("@") && email.contains(".")) {
                break;
            }
            System.out.println("Invalid email format. Try again.");
        }

        Client parameterizedClient = new Client("Tp4", "Student", email, "tp4-password");
        Client copiedClient = new Client(parameterizedClient);

        Client[] clients = {defaultClient, parameterizedClient, copiedClient};
        String clientState = "";
        for (int i = 0; i < clients.length; i++) {
            System.out.println();
            switch (i) {
                case 0 -> clientState = "Default Client";
                case 1 -> clientState = "Parameterized Client";
                case 2 -> clientState = "Copied Client";
                default -> clientState = "Unknown";
            }
            System.out.println("--- Client #" + (i + 1) + " -- " + clientState + " ---");
            clients[i].afficher();
            System.out.println(clients[i].toString());
        }

        System.out.println("=== End of TP4 Demo ===");
    }

    private static void runTp4Ex4Demo() {
        System.out.println();
        System.out.println("=== TP4 Exercice 4 Demo ===");

        PersonneCollection collection = new PersonneCollectionImpl();

        NouveauClient nouveauClient = new NouveauClient("Nina", "Roux", "nina.roux@example.com", "pwd");
        AncienClient ancienClient = new AncienClient("Omar", "Petit", "omar.petit@example.com", "pwd");
        ancienClient.recordReservation();
        ancienClient.recordReservation();
        ancienClient.recordReservation();

        Client defaultClient = new Client();

        collection.ajouter(ancienClient);
        collection.ajouter(defaultClient);
        collection.ajouter(nouveauClient);

        List<Personne> personnes = new ArrayList<>(collection.getAll());

        System.out.println("-- Before sort --");
        for (Personne personne : personnes) {
            System.out.println(personne.getFullName() + " | createdAt=" + personne.getCreatedAt());
        }

        Collections.sort(personnes);

        System.out.println("-- After sort (Comparable<Personne>) --");
        for (Personne personne : personnes) {
            System.out.println(personne.getFullName() + " | createdAt=" + personne.getCreatedAt());
        }

        System.out.println("-- instanceof / casting demo --");
        for (Personne personne : personnes) {
            if (personne instanceof AncienClient ancien) {
                System.out.println(ancien.getFullName() + " => AncienClient, discount=" + (int) (ancien.getDiscountRate() * 100) + "%");
            } else if (personne instanceof NouveauClient nouveau) {
                System.out.println(nouveau.getFullName() + " => NouveauClient, discount=" + (int) (nouveau.getDiscountRate() * 100) + "%");
            } else if (personne instanceof Client client) {
                System.out.println(client.getFullName() + " => Client, type=" + client.getClientType());
            }
        }

        System.out.println("=== End TP4 Exercice 4 Demo ===");
    }
}
