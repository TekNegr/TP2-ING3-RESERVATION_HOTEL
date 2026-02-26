import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CLI {
    private final Database db;
    private final Scanner scanner;
    private User currentUser = null;

    public CLI(Database db) {
        this.db = db;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the Housing CLI. Type 'help' for commands.");
        boolean running = true;
        while (running) {
            if (currentUser != null) {
                System.out.print("("+currentUser.getName()+")> ");
            } else {
                System.out.print("(Guest)> ");
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String args = parts.length > 1 ? parts[1].trim() : "";
            switch (cmd) {
                case "help":
                    printHelp();
                    break;
                case "register":
                    handleRegister(args);
                    break;
                case "login":
                    handleLogin(args);
                    break;
                case "logout":
                    handleLogout();
                    break;
                case "list_housings":
                    handleListHousings();
                    break;
                case "list_users":
                    handleListUsers();
                    break;
                case "add_housing":
                    handleAddHousing(args);
                    break;
                case "reserve":
                    handleReserve(args);
                    break;
                case "list_reservations":
                    handleListReservations();
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
                case "clear":
                case "cls":
                    handleClear();
                    break;
                default:
                    System.out.println("Unknown command: " + cmd);
            }
        }
        System.out.println("Goodbye.");
    }

    private void printHelp() {

        System.out.println("Commands:");
        System.out.println("  register <name>|<email>|<password>");
        System.out.println("  login <email> <password>");
        System.out.println("  logout");
        System.out.println("  add_housing <name>|<address>|<type>|<maxCapacity>|<price>|<description>");
        System.out.println("  list_housings");
        System.out.println("  list_users");
        System.out.println("  reserve <housingIndex> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
        System.out.println("  list_reservations");
        System.out.println("  get_housing_by_id <id>");
        System.out.println("  get_housing_by_name <name>");
        System.out.println("  get_housing_by_address <address>");
        System.out.println("  get_reservation_by_id <id>");
        System.out.println("  get_reservations_by_housing_name <name>");
        System.out.println("  get_reservations_by_housing_address <address>");
        System.out.println("  exit");
        System.out.println("  clear | cls   - clear the screen (may not work in all consoles)");
    }

    private void handleRegister(String args) {
        String[] toks = args.split("\\|");
        if (toks.length < 3) {
            System.out.println("Usage: register <name>|<email>|<password>");
            return;
        }
        String name = toks[0].trim();
        String email = toks[1].trim();
        String password = toks[2].trim();
        if (db.findUserByEmail(email) != null) {
            System.out.println("Email already registered.");
            return;
        }
        User u = new User(name, email, password);
        db.addUser(u);
        System.out.println("Registered user: " + email);
    }

    private void handleLogin(String args) {
        String[] toks = args.split(" ");
        if (toks.length < 2) {
            System.out.println("Usage: login <email> <password>");
            return;
        }
        String email = toks[0].trim();
        String password = toks[1].trim();
        if (db.authenticate(email, password)) {
            currentUser = db.findUserByEmail(email);
            System.out.println("Logged in as " + currentUser.getName());
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void handleLogout() {
        if (currentUser == null) {
            System.out.println("Not logged in.");
            return;
        }
        System.out.println("Logged out: " + currentUser.getEmail());
        currentUser = null;
    }

    private void handleListHousings() {
        List<Housing> hs = db.listHousings();
        if (hs.isEmpty()) {
            System.out.println("No housings available.");
            return;
        }
        for (int i = 0; i < hs.size(); i++) {
            System.out.println(i + ": " + hs.get(i).getName() + " - " + hs.get(i).getAdress() + " (" + hs.get(i).getType() + ")");
        }
    }

    private void handleListUsers() {
        List<User> us = db.listUsers();
        for (int i = 0; i < us.size(); i++) {
            System.out.println(i + ": " + us.get(i).getEmail() + " (" + us.get(i).getName() + ")");
        }
    }

    private void handleAddHousing(String args) {
        if (currentUser == null) {
            System.out.println("Login required to add housing.");
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
            HousingType type = HousingType.valueOf(toks[2].trim().toUpperCase());
            int maxCap = Integer.parseInt(toks[3].trim());
            double price = Double.parseDouble(toks[4].trim());
            String desc = toks[5].trim();
            Housing h = new Housing(name, address, type, maxCap, price, desc, new Reservation[0], new Amenity[0], new Rating[0], currentUser);
            db.addHousing(h);
            System.out.println("Housing added: " + name);
        } catch (Exception e) {
            System.out.println("Failed to add housing: " + e.getMessage());
        }
    }

    private void handleReserve(String args) {
        if (currentUser == null) {
            System.out.println("Login required to make a reservation.");
            return;
        }
        String[] toks = args.split(" ");
        if (toks.length < 4) {
            System.out.println("Usage: reserve <housingIndex> <startYYYY-MM-DD> <endYYYY-MM-DD> <guests>");
            return;
        }
        try {
            int idx = Integer.parseInt(toks[0].trim());
            String startS = toks[1].trim();
            String endS = toks[2].trim();
            int n_guests = Integer.parseInt(toks[3].trim());
            Housing h = db.getHousing(idx);
            if (h == null) {
                System.out.println("Invalid housing index.");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startS);
            Date end = sdf.parse(endS);
            Reservation r = new Reservation(currentUser, h, start, end, n_guests);
            db.addReservation(r);
            System.out.println("Reservation created for " + h.getName());
        } catch (Exception e) {
            System.out.println("Failed to create reservation: " + e.getMessage());
        }
    }

    private void handleListReservations() {
        List<Reservation> rs = db.listReservations();
        if (rs.isEmpty()) {
            System.out.println("No reservations.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < rs.size(); i++) {
            Reservation r = rs.get(i);
            System.out.println(i + ": " + r.getUser().getEmail() + " -> " + r.getHousing().getName() + " from " + sdf.format(r.reservationDates()[0]) + " to " + sdf.format(r.reservationDates()[1]) + " (" + r.getNumberOfGuests() + " guests)");
        }
    }

    private void handleClear() {
        try {
            // Try ANSI escape sequence first
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception ignored) {
        }
        // Fallback: print several new lines to emulate a clear
        for (int i = 0; i < 50; i++) System.out.println();
    }

    private void handleGetHousingById(String args) {
        try {
            int id = Integer.parseInt(args.trim());
            Housing h = db.getHousingById(id);
            if (h == null) {
                System.out.println("Housing not found for id: " + id);
            } else {
                System.out.println(h.get());
            }
        } catch (Exception e) {
            System.out.println("Usage: get_housing_by_id <id>");
        }
    }

    private void handleGetHousingByName(String args) {
        String name = args.trim();
        if (name.isEmpty()) { System.out.println("Usage: get_housing_by_name <name>"); return; }
        Housing h = db.getHousingByName(name);
        if (h == null) System.out.println("Housing not found for name: " + name);
        else System.out.println(h.get());
    }

    private void handleGetHousingByAddress(String args) {
        String address = args.trim();
        if (address.isEmpty()) { System.out.println("Usage: get_housing_by_address <address>"); return; }
        Housing h = db.getHousingByAddress(address);
        if (h == null) System.out.println("Housing not found for address: " + address);
        else System.out.println(h.get());
    }

    private void handleGetReservationById(String args) {
        try {
            int id = Integer.parseInt(args.trim());
            Reservation r = db.getReservationById(id);
            if (r == null) System.out.println("Reservation not found for id: " + id);
            else System.out.println(r.getDetails());
        } catch (Exception e) {
            System.out.println("Usage: get_reservation_by_id <id>");
        }
    }

    private void handleGetReservationsByHousingName(String args) {
        String name = args.trim();
        if (name.isEmpty()) { System.out.println("Usage: get_reservations_by_housing_name <name>"); return; }
        List<Reservation> list = db.getReservationsByHousingName(name);
        if (list.isEmpty()) System.out.println("No reservations for housing: " + name);
        else for (Reservation r : list) System.out.println(r.getDetails());
    }

    private void handleGetReservationsByHousingAddress(String args) {
        String address = args.trim();
        if (address.isEmpty()) { System.out.println("Usage: get_reservations_by_housing_address <address>"); return; }
        List<Reservation> list = db.getReservationsByHousingAddress(address);
        if (list.isEmpty()) System.out.println("No reservations for address: " + address);
        else for (Reservation r : list) System.out.println(r.getDetails());
    }
}
