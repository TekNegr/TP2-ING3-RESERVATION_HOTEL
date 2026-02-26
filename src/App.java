public class App {
    public static void main(String[] args) {
        Database db = new Database();
        CLI cli = new CLI(db);
        cli.run();
    }
}

