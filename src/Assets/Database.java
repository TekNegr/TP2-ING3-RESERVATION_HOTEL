package Assets;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Client> clients;
    private final List<Housing> housings;
    private final List<Reservation> reservations;
    private int nextHousingId;
    private int nextReservationId;

    public Database() {
        clients = new ArrayList<>();
        housings = new ArrayList<>();
        reservations = new ArrayList<>();
        nextHousingId = 1;
        nextReservationId = 1;
        seedData();
    }

    private void seedData() {
        NouveauClient n1 = new NouveauClient("Alice", "Martin", "alice@example.com", "password123");
        AncienClient a1 = new AncienClient("Bob", "Durand", "bob@example.com", "password456");
        a1.recordReservation();
        a1.recordReservation();
        a1.recordReservation();
        clients.add(n1);
        clients.add(a1);
    }

    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        if (findClientByEmail(client.getEmail()) != null) {
            throw new IllegalStateException("A client with this email already exists.");
        }
        clients.add(client);
    }

    public Client findClientByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        for (Client client : clients) {
            if (client.getEmail().equalsIgnoreCase(email)) return client;
        }
        return null;
    }

    public Client authenticate(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        Client client = findClientByEmail(email);
        if (client != null && client.verifyPassword(password)) return client;
        return null;
    }

    public List<Client> listClients() {
        return clients;
    }

    public void addHousing(Housing housing) {
        if (housing == null) {
            throw new IllegalArgumentException("Housing cannot be null.");
        }
        housing.setId(nextHousingId++);
        housings.add(housing);
        if (housing.getOwner() != null) {
            housing.getOwner().addOwnedHousing(housing);
        }
    }

    public List<Housing> listHousings() {
        return housings;
    }

    public boolean removeHousing(Housing housing) {
        boolean removed = housings.remove(housing);
        if (removed && housing.getOwner() != null) {
            housing.getOwner().removeOwnedHousing(housing);
        }
        return removed;
    }

    public Housing getHousingById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Housing id must be greater than 0.");
        }
        for (Housing housing : housings) if (housing.getId() == id) return housing;
        return null;
    }

    public Housing getHousingByName(String name) {
        if (name == null) return null;
        for (Housing housing : housings) if (name.equalsIgnoreCase(housing.getName())) return housing;
        return null;
    }

    public Housing getHousingByAddress(String address) {
        if (address == null) return null;
        for (Housing housing : housings) if (address.equalsIgnoreCase(housing.getAddress())) return housing;
        return null;
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }
        reservation.setId(nextReservationId++);
        reservations.add(reservation);
        reservation.getClient().addMyReservation(reservation);
        if (reservation.getClient() instanceof AncienClient ancienClient) {
            ancienClient.recordReservation();
        }
    }

    public Reservation getReservationById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Reservation id must be greater than 0.");
        }
        for (Reservation reservation : reservations) if (reservation.getId() == id) return reservation;
        return null;
    }

    public List<Reservation> getReservationsByHousingName(String housingName) {
        List<Reservation> output = new ArrayList<>();
        if (housingName == null) return output;
        for (Reservation reservation : reservations) {
            Housing housing = reservation.getHousing();
            if (housing != null && housingName.equalsIgnoreCase(housing.getName())) output.add(reservation);
        }
        return output;
    }

    public List<Reservation> getReservationsByHousingAddress(String housingAddress) {
        List<Reservation> output = new ArrayList<>();
        if (housingAddress == null) return output;
        for (Reservation reservation : reservations) {
            Housing housing = reservation.getHousing();
            if (housing != null && housingAddress.equalsIgnoreCase(housing.getAddress())) output.add(reservation);
        }
        return output;
    }

    public List<Reservation> listReservations() {
        return reservations;
    }

    public int cancelFutureReservationsForHousing(Housing housing, LocalDate today) {
        if (housing == null) {
            throw new IllegalArgumentException("Housing is required.");
        }
        if (today == null) {
            throw new IllegalArgumentException("Current date is required.");
        }
        int cancelled = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getHousing() == null || reservation.getHousing() != housing) continue;
            if (!reservation.getStartDate().isBefore(today) && reservation.getStatus() != ReservationStatus.CANCELLED) {
                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    housing.removeUnavailablePeriod(reservation.getStartDate(), reservation.getEndDate());
                }
                reservation.cancel();
                cancelled++;
            }
        }
        return cancelled;
    }

    public int cancelFutureReservationsForClient(Client client, LocalDate today) {
        if (client == null) {
            throw new IllegalArgumentException("Client is required.");
        }
        if (today == null) {
            throw new IllegalArgumentException("Current date is required.");
        }
        int cancelled = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getClient() == null || reservation.getClient() != client) continue;
            if (!reservation.getStartDate().isBefore(today) && reservation.getStatus() != ReservationStatus.CANCELLED) {
                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    reservation.getHousing().removeUnavailablePeriod(reservation.getStartDate(), reservation.getEndDate());
                }
                reservation.cancel();
                cancelled++;
            }
        }
        return cancelled;
    }

    public int cancelFutureReservationsForOwner(Client owner, LocalDate today) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner is required.");
        }
        if (today == null) {
            throw new IllegalArgumentException("Current date is required.");
        }
        int cancelled = 0;
        for (Reservation reservation : reservations) {
            Housing housing = reservation.getHousing();
            if (housing == null || housing.getOwner() != owner) continue;
            if (!reservation.getStartDate().isBefore(today) && reservation.getStatus() != ReservationStatus.CANCELLED) {
                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    housing.removeUnavailablePeriod(reservation.getStartDate(), reservation.getEndDate());
                }
                reservation.cancel();
                cancelled++;
            }
        }
        return cancelled;
    }

    public List<Housing> getHousingsByOwner(Client owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner is required.");
        }
        List<Housing> output = new ArrayList<>();
        for (Housing housing : housings) {
            if (housing.getOwner() == owner) output.add(housing);
        }
        return output;
    }

    public boolean removeClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        return clients.remove(client);
    }
}
