package Assets;

public class NouveauClient extends Client {
    public NouveauClient(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    @Override
    public double getDiscountRate() {
        return 0.0;
    }

    @Override
    public String getClientType() {
        return "NOUVEAU_CLIENT";
    }

    @Override
    public void afficher() {
        super.afficher();
        System.out.println("--- Loyalty & Discount ---");
        System.out.println("  Discount rate : none (new customer, no history)");
    }
}
