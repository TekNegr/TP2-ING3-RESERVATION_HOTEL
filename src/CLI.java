import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

public class CLI {
    private User[] users;
    private Housing[] housings;
    private Reservation[] reservations;

    public User[] sampleUsers ={
        new User("Alice", "alice@example.com", "password123"),
        new User("Bob", "bob@example.com", "password456"),
        new User("Charlie", "charlie@example.com", "password789")
    };

    public Amenity[] sampleAmenities = {
        new Amenity("WiFi", "High-speed wireless internet"),
        new Amenity("Pool", "Outdoor swimming pool"),
        new Amenity("Parking", "Free parking on premises")
    };

    public Housing[] sampleHousings = {
        new Housing("Sample House", "123 Main St", HousingType.APARTEMENT, 4, 150.0, "A sample house for testing purposes", sampl, sampleAmenities, null)
    };

    public Reservation[] sampleReservations = {
        new Reservation(sampleUsers[0], sampleHousings[0], new Date(), getDatePlusDays(new Date(), 2), 2)
    };

    public CLI() {
        users = new User[10];
        housings = new Housing[10];
        reservations = new Reservation[10];

        for (int i = 0; i < users.length; i++) {
            users[i] = new User("User" + (i + 1), "user" + (i + 1) + "@example.com", "password" + (i + 1));
        }
    }

    private static Date getDatePlusDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public String executeCLI(){
        String command_res = "";
        String command_req = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print(">  ");
        command_req = scanner.nextLine();
        command_req.split(" ");
        String action = command_req.split(" ")[0];
        String target = command_req.split(" ")[1];
        String parameters = command_req.substring(action.length() + target.length() + 2);
        switch (action) {
            case "test": command_res = "Test command executed with target: " + target + " and parameters: " + parameters;
            case "add": addObject(target, parameters);
            case "list": listObjects(target);
            case "delete": deleteObject(target, parameters);
            case "update": updateObject(target, parameters);
            case "exit": {command_res = "exit"; scanner.close();}
                break;
            default:
                command_res = "Unknown action: " + action;
        }

        return command_res;
    }

    public static void addObject(String target, String parameters){
        switch (target) {
            case "user":
                // Parse parameters and create a new User object
                break;
            case "housing":
                // Parse parameters and create a new Housing object
                break;
            case "reservation":
                // Parse parameters and create a new Reservation object
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    public static void listObjects(String target){
        switch (target) {
            case "users":
                // List all User objects
                break;
            case "housings":
                // List all Housing objects
                break;
            case "reservations":
                // List all Reservation objects
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    public static void deleteObject(String target, String parameters){
        switch (target) {
            case "user":
                // Parse parameters and delete the specified User object
                break;
            case "housing":
                // Parse parameters and delete the specified Housing object
                break;
            case "reservation":
                // Parse parameters and delete the specified Reservation object
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

    public static void updateObject(String target, String parameters){
        switch (target) {
            case "user":
                // Parse parameters and update the specified User object
                break;
            case "housing":
                // Parse parameters and update the specified Housing object
                break;
            case "reservation":
                // Parse parameters and update the specified Reservation object
                break;
            default:
                System.out.println("Unknown target: " + target);
        }
    }

}
