import java.util.ArrayList;
import java.util.List;

public class Database {
	private final List<User> users;
	private final List<Housing> housings;
	private final List<Reservation> reservations;
	private final List<Rating> ratings;
	private int nextHousingId = 1;
	private int nextReservationId = 1;

	public Database() {
		this.users = new ArrayList<>();
		this.housings = new ArrayList<>();
		this.reservations = new ArrayList<>();
		this.ratings = new ArrayList<>();

		// small sample data
		users.add(new User("Alice", "alice@example.com", "password123"));
		users.add(new User("Bob", "bob@example.com", "password456"));
	}

	// Users
	public void addUser(User user) {
		users.add(user);
	}

	public User findUserByEmail(String email) {
		for (User u : users) {
			if (u != null && u.getEmail().equalsIgnoreCase(email)) return u;
		}
		return null;
	}

	public boolean authenticate(String email, String password) {
		User u = findUserByEmail(email);
		return u != null && u.verifyPassword(password);
	}

	public List<User> listUsers() {
		return users;
	}

	// Housings
	public void addHousing(Housing h) {
		if (h != null) {
			h.setId(nextHousingId++);
			housings.add(h);
		}
	}

	public List<Housing> listHousings() {
		return housings;
	}

	public Housing getHousing(int index) {
		if (index < 0 || index >= housings.size()) return null;
		return housings.get(index);
	}

	// Reservations
	public void addReservation(Reservation r) {
		if (r != null) {
			r.setId(nextReservationId++);
			reservations.add(r);
		}
	}

	// Lookup methods
	public Housing getHousingById(int id) {
		for (Housing h : housings) if (h.getId() == id) return h;
		return null;
	}

	public Housing getHousingByName(String name) {
		if (name == null) return null;
		for (Housing h : housings) if (name.equalsIgnoreCase(h.getName())) return h;
		return null;
	}

	public Housing getHousingByAddress(String address) {
		if (address == null) return null;
		for (Housing h : housings) if (address.equalsIgnoreCase(h.getAdress())) return h;
		return null;
	}

	public Reservation getReservationById(int id) {
		for (Reservation r : reservations) if (r.getId() == id) return r;
		return null;
	}

	public List<Reservation> getReservationsByHousingName(String housingName) {
		List<Reservation> out = new ArrayList<>();
		if (housingName == null) return out;
		for (Reservation r : reservations) {
			Housing h = r.getHousing();
			if (h != null && housingName.equalsIgnoreCase(h.getName())) out.add(r);
		}
		return out;
	}

	public List<Reservation> getReservationsByHousingAddress(String address) {
		List<Reservation> out = new ArrayList<>();
		if (address == null) return out;
		for (Reservation r : reservations) {
			Housing h = r.getHousing();
			if (h != null && address.equalsIgnoreCase(h.getAdress())) out.add(r);
		}
		return out;
	}

	public List<Reservation> listReservations() {
		return reservations;
	}

	// Ratings
	public void addRating(Rating r) {
		ratings.add(r);
	}

	public List<Rating> listRatings() {
		return ratings;
	}
}
