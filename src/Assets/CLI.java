package Assets;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Database db;
    private final Scanner scanner;
    private Client currentClient;

    public CLI(Database db) {
        this.db = db;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        printWelcome();
        boolean running = true;

        while (running) {
            System.out.print("(" + (currentClient == null ? "Guest" : currentClient.getEmail()) + ")> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1].trim() : "";

            switch (cmd) {
                case "help":
                    printHelp();
                    break;
                case "register_new":
                    handleRegisterNew(args);
                    break;
                case "register_old":
                    handleRegisterOld(args);
                    break;
                case "login":
                    handleLogin(args);
                    break;
                case "logout":
                    currentClient = null;
                    System.out.println("Logged out.");
                    break;
                case "me":
                    handleMe();
                    break;
                case "add_housing":
                    handleAddHousing(args);
                    break;
                case "remove_housing":
                    handleRemoveHousing(args);
                    break;
                case "edit_housing":
                    handleEditHousing(args);
                    break;
                case "add_amenity":
                    handleAddAmenity(args);
                    break;
                case "edit_amenities":
                    handleEditAmenities(args);
                    break;
                case "remove_amenity":
                    handleRemoveAmenity(args);
                    break;
                case "list_housings":
                    handleListHousings();
                    break;
                case "list_users":
                    handleListUsers();
                    break;
                case "reserve":
                    handleReserve(args);
                    break;
                case "edit_reservation":
                    handleEditReservation(args);
                    break;
                case "confirm_reservation":
                    handleConfirmReservation(args);
                    break;
                case "cancel_reservation":
                    handleCancelReservation(args);
                    break;
                case "rate_house":
                    handleRateHouse(args);
                    break;
                case "list_reservations":
                    handleListReservations();
                    break;
                case "delete_account":
                    handleDeleteAccount();
                    break;
                case "get_housing_by_id":
                    handleGetHousingById(args);
                    break;
                case "get_housing_by_name":
                    handleGetHousingByName(args);
                    break;
                case "get_housing_by_address":
                    handleGetHousingByAddress(args);
                    break;
                case "get_reservation_by_id":
                    handleGetReservationById(args);
                    break;
                case "get_reservations_by_housing_name":
                    handleGetReservationsByHousingName(args);
                    break;
                case "get_reservations_by_housing_address":
                    handleGetReservationsByHousingAddress(args);
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown command: " + cmd);
            }
        }

        System.out.println("Goodbye.");
    }

    private void printWelcome() {
        System.out.println("Welcome to the Hotel Reservation System!");
        System.out.println();
        System.out.println("=== Registration Types ===");
        System.out.println("Nouveau Client: New customers with no loyalty history. No discount.");
        System.out.println("Ancien Client: Loyal/returning customers. Eligible for discount benefits.");
        System.out.println();
        System.out.println("Type 'help' to list all commands.");
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  register_new <firstName>|<lastName>|<email>|<password>");
        System.out.println("  register_old <firstName>|<lastName>|<email>|<password>");
        System.out.println("  login <email> <password>");
        System.out.println("  logout");
        System.out.println("  me");
        if (currentClient != null) {
            System.out.println("  add_housing <name>|<address>|<type>|<maxCapacity>|<price>|<description>");
            System.out.println("    type values: HOTEL, APARTMENT, HOUSE, VILLA, CABIN (aliases accepted: APARTEMENT, APPARTEMENT)");
            System.out.println("  edit_housing <housingId>|<name>|<address>|<type>|<maxCapacity>|<price>|<description>");
            System.out.println("  remove_housing <housingId>");
            System.out.println("  add_amenity <housingId>|<amenityName>|<description>");
            System.out.println("  edit_amenities <housingId>|<oldAmenityName>|<newAmenityName>|<newDescription>");
            System.out.println("  remove_amenity <housingId>|<amenityName>");
            System.out.println("  reserve <housingId> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
            System.out.println("  edit_reservation <reservationId> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
            System.out.println("  confirm_reservation <reservationId>");
            System.out.println("  cancel_reservation <reservationId>");
            System.out.println("  rate_house <housingId> <score(1-5)> <comment>");
            System.out.println("  delete_account");
        }
        System.out.println("  list_housings");
        System.out.println("  list_users");
        System.out.println("  list_reservations");
        System.out.println("  get_housing_by_id <id>");
        System.out.println("  get_housing_by_name <name>");
        System.out.println("  get_housing_by_address <address>");
        System.out.println("  get_reservation_by_id <id>");
        System.out.println("  get_reservations_by_housing_name <name>");
        System.out.println("  get_reservations_by_housing_address <address>");
        System.out.println("  exit");
    }

    private void handleRegisterNew(String args) {
        String[] toks = args.split("\\|");
        if (toks.length < 4) {
            System.out.println("Usage: register_new <firstName>|<lastName>|<email>|<password>");
            return;
        }
        String firstName = toks[0].trim();
        String lastName = toks[1].trim();
        String email = toks[2].trim();
        String password = toks[3].trim();
        if (db.findClientByEmail(email) != null) {
            System.out.println("Email already exists.");
            return;
        }
        db.addClient(new NouveauClient(firstName, lastName, email, password));
        System.out.println("NouveauClient registered: " + email);
    }

    private void handleRegisterOld(String args) {
        String[] toks = args.split("\\|");
        if (toks.length < 4) {
            System.out.println("Usage: register_old <firstName>|<lastName>|<email>|<password>");
            return;
        }
        String firstName = toks[0].trim();
        String lastName = toks[1].trim();
        String email = toks[2].trim();
        String password = toks[3].trim();
        if (db.findClientByEmail(email) != null) {
            System.out.println("Email already exists.");
            return;
        }
        db.addClient(new AncienClient(firstName, lastName, email, password));
        System.out.println("AncienClient registered: " + email);
    }

    private void handleLogin(String args) {
        String[] toks = args.split(" ");
        if (toks.length < 2) {
            System.out.println("Usage: login <email> <password>");
            return;
        }
        Client authenticated = db.authenticate(toks[0].trim(), toks[1].trim());
        if (authenticated == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        currentClient = authenticated;
        System.out.println("Logged in as " + currentClient.getFullName() + " (" + currentClient.getClientType() + ")");
    }

    private void handleMe() {
        if (currentClient == null) {
            System.out.println("Not logged in.");
            return;
        }
        System.out.println("Name: " + currentClient.getFullName());
        System.out.println("Email: " + currentClient.getEmail());
        System.out.println("Type: " + currentClient.getClientType());
        System.out.println("Discount: " + (int) (currentClient.getDiscountRate() * 100) + "%");
    }

    private void handleAddHousing(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split("\\|");
        if (toks.length < 6) {
            System.out.println("Usage: add_housing <name>|<address>|<type>|<maxCapacity>|<price>|<description>");
            return;
        }
        try {
            String name = toks[0].trim();
            String address = toks[1].trim();
            HousingType type = parseHousingType(toks[2].trim());
            int maxCapacity = Integer.parseInt(toks[3].trim());
            double price = Double.parseDouble(toks[4].trim());
            String description = toks[5].trim();

            Housing housing = new Housing(name, address, type, maxCapacity, price, description, currentClient);
            db.addHousing(housing);
            System.out.println("Housing added with id=" + housing.getId());
        } catch (Exception e) {
            System.out.println("Cannot add housing: " + e.getMessage());
        }
    }

    private void handleRemoveHousing(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        try {
            int housingId = Integer.parseInt(args.trim());
            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can remove this housing.");
                return;
            }
            int cancelled = db.cancelFutureReservationsForHousing(housing, LocalDate.now());
            db.removeHousing(housing);
            System.out.println("Housing removed. Future reservations cancelled: " + cancelled);
        } catch (Exception e) {
            System.out.println("Usage: remove_housing <housingId>");
        }
    }

    private void handleEditHousing(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split("\\|");
        if (toks.length < 7) {
            System.out.println("Usage: edit_housing <housingId>|<name>|<address>|<type>|<maxCapacity>|<price>|<description>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can edit this housing.");
                return;
            }

            housing.setName(toks[1].trim());
            housing.setAddress(toks[2].trim());
            housing.setType(parseHousingType(toks[3].trim()));
            housing.setMaxCapacity(Integer.parseInt(toks[4].trim()));
            housing.setPricePerNight(Double.parseDouble(toks[5].trim()));
            housing.setDescription(toks[6].trim());
            System.out.println("Housing updated: id=" + housing.getId());
        } catch (Exception e) {
            System.out.println("Cannot edit housing: " + e.getMessage());
        }
    }

    private void handleAddAmenity(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split("\\|");
        if (toks.length < 3) {
            System.out.println("Usage: add_amenity <housingId>|<amenityName>|<description>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            String amenityName = toks[1].trim();
            String description = toks[2].trim();

            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can add amenities.");
                return;
            }
            for (Amenity amenity : housing.getAmenities()) {
                if (amenity.getName().equalsIgnoreCase(amenityName)) {
                    System.out.println("Amenity already exists for this housing.");
                    return;
                }
            }
            housing.addAmenity(new Amenity(amenityName, description));
            System.out.println("Amenity added to housing #" + housingId + ": " + amenityName);
        } catch (Exception e) {
            System.out.println("Cannot add amenity: " + e.getMessage());
        }
    }

    private void handleEditAmenities(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split("\\|");
        if (toks.length < 4) {
            System.out.println("Usage: edit_amenities <housingId>|<oldAmenityName>|<newAmenityName>|<newDescription>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            String oldAmenityName = toks[1].trim();
            String newAmenityName = toks[2].trim();
            String newDescription = toks[3].trim();

            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can edit amenities.");
                return;
            }

            Amenity target = null;
            for (Amenity amenity : housing.getAmenities()) {
                if (amenity.getName().equalsIgnoreCase(oldAmenityName)) {
                    target = amenity;
                    break;
                }
            }
            if (target == null) {
                System.out.println("Amenity not found: " + oldAmenityName);
                return;
            }
            for (Amenity amenity : housing.getAmenities()) {
                if (amenity != target && amenity.getName().equalsIgnoreCase(newAmenityName)) {
                    System.out.println("Another amenity already uses this name: " + newAmenityName);
                    return;
                }
            }

            target.setName(newAmenityName);
            target.setDescription(newDescription);
            System.out.println("Amenity updated for housing #" + housingId + ": " + oldAmenityName + " -> " + newAmenityName);
        } catch (Exception e) {
            System.out.println("Cannot edit amenity: " + e.getMessage());
        }
    }

    private void handleRemoveAmenity(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split("\\|");
        if (toks.length < 2) {
            System.out.println("Usage: remove_amenity <housingId>|<amenityName>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            String amenityName = toks[1].trim();

            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can remove amenities.");
                return;
            }

            boolean removed = housing.getAmenities().removeIf(a -> a.getName().equalsIgnoreCase(amenityName));
            if (!removed) {
                System.out.println("Amenity not found: " + amenityName);
                return;
            }
            System.out.println("Amenity removed from housing #" + housingId + ": " + amenityName);
        } catch (Exception e) {
            System.out.println("Cannot remove amenity: " + e.getMessage());
        }
    }

    private HousingType parseHousingType(String rawType) {
        if (rawType == null || rawType.trim().isEmpty()) {
            throw new IllegalArgumentException("Housing type is required.");
        }
        String normalized = rawType.trim().toUpperCase();
        if ("APARTEMENT".equals(normalized) || "APPARTEMENT".equals(normalized)) {
            normalized = "APARTMENT";
        }
        try {
            return HousingType.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid type '" + rawType + "'. Use HOTEL, APARTMENT, HOUSE, VILLA, CABIN.");
        }
    }

    private void handleListHousings() {
        List<Housing> list = db.listHousings();
        if (list.isEmpty()) {
            System.out.println("No housing available.");
            return;
        }
        for (Housing housing : list) {
            System.out.println(housing.getSummary());
        }
    }

    private void handleListUsers() {
        List<Client> clients = db.listClients();
        if (clients.isEmpty()) {
            System.out.println("No users.");
            return;
        }
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            System.out.println(i + ": " + client.getEmail() + " (" + client.getFullName() + ", " + client.getClientType() + ")");
        }
    }

    private void handleReserve(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split(" ");
        if (toks.length < 4) {
            System.out.println("Usage: reserve <housingId> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            LocalDate start = LocalDate.parse(toks[1].trim());
            LocalDate end = LocalDate.parse(toks[2].trim());
            int guests = Integer.parseInt(toks[3].trim());

            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }
            if (guests <= 0 || guests > housing.getMaxCapacity()) {
                System.out.println("Invalid number of guests for this housing.");
                return;
            }
            if (!housing.isAvailable(start, end)) {
                System.out.println("Housing not available for selected dates.");
                return;
            }

            Reservation reservation = new Reservation(currentClient, housing, start, end, guests);
            reservation.applyDiscount(currentClient.getDiscountRate());
            db.addReservation(reservation);
            System.out.println("Reservation created as PENDING: " + reservation.getDetails());
            System.out.println("Owner must confirm with: confirm_reservation " + reservation.getId());
        } catch (Exception e) {
            System.out.println("Cannot create reservation: " + e.getMessage());
        }
    }

    private void handleEditReservation(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split(" ");
        if (toks.length < 4) 
            {
            System.out.println("Usage: edit_reservation <reservationId> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
            return;
        }
        try {
            int reservationId = Integer.parseInt(toks[0].trim());
            LocalDate newStart = LocalDate.parse(toks[1].trim());
            LocalDate newEnd = LocalDate.parse(toks[2].trim());
            int newGuests = Integer.parseInt(toks[3].trim());

            Reservation reservation = db.getReservationById(reservationId);
            if (reservation == null) {
                System.out.println("Reservation not found.");
                return;
            }
            if (reservation.getClient() != currentClient || reservation.getHousing().getOwner() != currentClient) {
                System.out.println("You can only edit your own reservation.");
                return;
            }
            if (reservation.getStatus() == ReservationStatus.CANCELLED) {
                System.out.println("Cannot edit a cancelled reservation.");
                return;
            }
            if (reservation.getStartDate().isBefore(LocalDate.now())) {
                System.out.println("Cannot edit past reservations.");
                return;
            }
            if (!newStart.isBefore(newEnd)) {
                System.out.println("Start date must be before end date.");
                return;
            }

            Housing housing = reservation.getHousing();
            if (newGuests <= 0 || newGuests > housing.getMaxCapacity()) 
                {
                System.out.println("Invalid number of guests for this housing.");
                return;
            }

            boolean wasConfirmed = reservation.getStatus() == ReservationStatus.CONFIRMED;
            LocalDate oldStart = reservation.getStartDate();
            LocalDate oldEnd = reservation.getEndDate();

            if (wasConfirmed) {
                housing.removeUnavailablePeriod(oldStart, oldEnd);
            }
            if (!housing.isAvailable(newStart, newEnd)) 
                {
                if (wasConfirmed) {
                    housing.addUnavailablePeriod(oldStart, oldEnd);
                }
                System.out.println("Housing not available for new dates.");
                return;
            }

            reservation.updateReservation(newStart, newEnd, newGuests);
            if (wasConfirmed) {
                housing.addUnavailablePeriod(newStart, newEnd);
            }

            System.out.println("Reservation updated: " + reservation.getDetails());
        } catch (Exception e) {
            System.out.println("Cannot edit reservation: " + e.getMessage());
        }
    }

    private void handleConfirmReservation(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        try {
            int reservationId = Integer.parseInt(args.trim());
            Reservation reservation = db.getReservationById(reservationId);
            if (reservation == null) {
                System.out.println("Reservation not found.");
                return;
            }
            Housing housing = reservation.getHousing();
            if (housing.getOwner() != currentClient) {
                System.out.println("Only owner can confirm reservation.");
                return;
            }
            if (reservation.getStatus() == ReservationStatus.CANCELLED) {
                System.out.println("Cannot confirm a cancelled reservation.");
                return;
            }
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                System.out.println("Reservation already confirmed.");
                return;
            }
            if (!housing.isAvailable(reservation.getStartDate(), reservation.getEndDate())) {
                System.out.println("Cannot confirm: housing is not available for these dates.");
                return;
            }
            reservation.confirm();
            housing.addUnavailablePeriod(reservation.getStartDate(), reservation.getEndDate());
            System.out.println("Reservation confirmed: #" + reservation.getId());
        } catch (Exception e) {
            System.out.println("Usage: confirm_reservation <reservationId>");
        }
    }

    private void handleCancelReservation(String args) {
        try {
            int id = Integer.parseInt(args.trim());
            Reservation reservation = db.getReservationById(id);
            if (reservation == null) {
                System.out.println("Reservation not found.");
                return;
            }
            if (reservation.getStatus() == ReservationStatus.CANCELLED) {
                System.out.println("Reservation already cancelled.");
                return;
            }
            if (currentClient == null) {
                System.out.println("Login required.");
                return;
            }
            boolean isClient = reservation.getClient().getEmail().equals(currentClient.getEmail());
            boolean isOwner = reservation.getHousing().getOwner().getEmail().equals(currentClient.getEmail());
            if (!isClient && !isOwner) {
                System.out.println("Only reservation client or housing owner can cancel.");
                return;
            }
            reservation.cancel();
            reservation.getHousing().removeUnavailablePeriod(reservation.getStartDate(), reservation.getEndDate());
            System.out.println("Reservation cancelled: #" + reservation.getId());
        } catch (Exception e) {
            System.out.println("Usage: cancel_reservation <reservationId>");
        }
    }

    private void handleRateHouse(String args) {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        String[] toks = args.split(" ", 3);
        if (toks.length < 3) {
            System.out.println("Usage: rate_house <housingId> <score(1-5)> <comment>");
            return;
        }
        try {
            int housingId = Integer.parseInt(toks[0].trim());
            int score = Integer.parseInt(toks[1].trim());
            String comment = toks[2].trim();

            Housing housing = db.getHousingById(housingId);
            if (housing == null) {
                System.out.println("Housing not found.");
                return;
            }

            boolean hasConfirmedReservation = false;
            for (Reservation reservation : db.listReservations()) {
                if (reservation.getClient() == currentClient
                        && reservation.getHousing() == housing
                        && reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    hasConfirmedReservation = true;
                    break;
                }
            }
            if (!hasConfirmedReservation) {
                System.out.println("You can rate only houses with at least one confirmed reservation.");
                return;
            }

            Rating rating = new Rating(currentClient, housing, score, comment, LocalDate.now());
            housing.addRating(rating);
            System.out.println("Rating added: " + rating.getDetails());
        } catch (Exception e) {
            System.out.println("Cannot add rating: " + e.getMessage());
        }
    }

    private void handleDeleteAccount() {
        if (currentClient == null) {
            System.out.println("Login required.");
            return;
        }
        LocalDate today = LocalDate.now();
        int cancelledAsClient = db.cancelFutureReservationsForClient(currentClient, today);
        int cancelledAsOwner = db.cancelFutureReservationsForOwner(currentClient, today);

        List<Housing> owned = db.getHousingsByOwner(currentClient);
        for (Housing housing : owned) {
            db.removeHousing(housing);
        }

        boolean deleted = db.removeClient(currentClient);
        currentClient = null;
        if (deleted) {
            System.out.println("Account deleted.");
            System.out.println("Future reservations cancelled (as client): " + cancelledAsClient);
            System.out.println("Future reservations cancelled (as owner): " + cancelledAsOwner);
            System.out.println("Owned housings removed: " + owned.size());
        } else {
            System.out.println("Account deletion failed.");
        }
    }

    private void handleListReservations() {
        List<Reservation> list = db.listReservations();
        if (list.isEmpty()) {
            System.out.println("No reservations.");
            return;
        }
        for (Reservation reservation : list) {
            System.out.println(reservation.getDetails());
        }
    }

    private void handleGetHousingById(String args) {
        try {
            int id = Integer.parseInt(args.trim());
            Housing housing = db.getHousingById(id);
            if (housing == null) {
                System.out.println("Housing not found for id: " + id);
                return;
            }
            System.out.println(housing.getDetails());
        } catch (Exception e) {
            System.out.println("Usage: get_housing_by_id <id>");
        }
    }

    private void handleGetHousingByName(String args) {
        String name = args.trim();
        if (name.isEmpty()) {
            System.out.println("Usage: get_housing_by_name <name>");
            return;
        }
        Housing housing = db.getHousingByName(name);
        if (housing == null) {
            System.out.println("Housing not found for name: " + name);
            return;
        }
        System.out.println(housing.getDetails());
    }

    private void handleGetHousingByAddress(String args) {
        String address = args.trim();
        if (address.isEmpty()) {
            System.out.println("Usage: get_housing_by_address <address>");
            return;
        }
        Housing housing = db.getHousingByAddress(address);
        if (housing == null) {
            System.out.println("Housing not found for address: " + address);
            return;
        }
        System.out.println(housing.getDetails());
    }

    private void handleGetReservationById(String args) {
        try {
            int id = Integer.parseInt(args.trim());
            Reservation reservation = db.getReservationById(id);
            if (reservation == null) {
                System.out.println("Reservation not found for id: " + id);
                return;
            }
            System.out.println(reservation.getDetails());
        } catch (Exception e) {
            System.out.println("Usage: get_reservation_by_id <id>");
        }
    }

    private void handleGetReservationsByHousingName(String args) {
        String name = args.trim();
        if (name.isEmpty()) {
            System.out.println("Usage: get_reservations_by_housing_name <name>");
            return;
        }
        List<Reservation> list = db.getReservationsByHousingName(name);
        if (list.isEmpty()) {
            System.out.println("No reservations for housing: " + name);
            return;
        }
        for (Reservation reservation : list) {
            System.out.println(reservation.getDetails());
        }
    }

    private void handleGetReservationsByHousingAddress(String args) {
        String address = args.trim();
        if (address.isEmpty()) {
            System.out.println("Usage: get_reservations_by_housing_address <address>");
            return;
        }
        List<Reservation> list = db.getReservationsByHousingAddress(address);
        if (list.isEmpty()) {
            System.out.println("No reservations for address: " + address);
            return;
        }
        for (Reservation reservation : list) {
            System.out.println(reservation.getDetails());
        }
    }
}
