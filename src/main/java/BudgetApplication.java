public class BudgetApplication {
    enum transactionType {
        INCOME,
        EXPENSE
    }

    static void main() {
        new BudgetApplication().runBudgetApplication();
    }

    //TODO! Metod som visar den interaktiva menyn som används i budgethanteraren.
    private void displayMenu() {
        String menu = """
                Budgethanteraren
                ================
                1. Lägg till transaktion
                2. Visa alla transaktioner
                3. Visa saldo och sammanställning per kategori
                4. Filtrera transaktioner
                5. Spara till fil
                e. Avsluta
                """;
        IO.println(menu);
    }

    //TODO! Metod för att köra programmet (budgethanteraren).
    private void runBudgetApplication() {
        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> IO.println("Lägg till transaktion");
                case "2" -> IO.println("Visa alla transaktioner");
                case "3" -> IO.println("Visa saldo och sammanställning per kategori");
                case "4" -> IO.println("Filtrera transaktioner");
                case "5" -> IO.println("Spara till fil");
                case "e", "E" -> running = false;
                default -> IO.println("Det går inte att välja, försök igen.");
            }
        } while (running);
    }

}
