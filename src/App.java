import Assets.CLI;
import Assets.Database;

public class App {
    public static void main(String[] args) {
        Database database = new Database();
        CLI cli = new CLI(database);
        cli.run();
    }
}
