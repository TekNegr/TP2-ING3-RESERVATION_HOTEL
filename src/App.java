public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String response = "";
        do{
            CLI cli = new CLI();
            response = cli.executeCLI();
            System.out.println(response);
        } while (!response.equals("exit"));
    }
}

