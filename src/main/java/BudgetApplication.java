import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;

public class BudgetApplication {
    private final Repository<Transaction> repository = new Repository<>();

    public enum TransactionType {
        INCOME,
        EXPENSE
    }

        static void main() {
        new BudgetApplication().runBudgetApplication();
    }

    // Metod som visar den interaktiva menyn som används i budgethanteraren.
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

    // Metod för att köra programmet (budgethanteraren).
    //TODO! Ändra menyvalen till funktioner!
    public void runBudgetApplication() {
        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> addTransaction();
                case "2" -> IO.println("Visa alla transaktioner");
                case "3" -> IO.println("Visa saldo och sammanställning per kategori");
                case "4" -> IO.println("Filtrera transaktioner");
                case "5" -> IO.println("Spara till fil");
                case "e", "E" -> running = false;
                default -> IO.println("Det går inte att välja, försök igen.");
            }
        } while (running);
    }

    /**
     * Metod skapad för att hantera användarens inmatning i Budgethanteraren. Tar användarens input,
     * gör validering av indatan innan metoden sen skickar vidare en new Transaction till Repository<T>.
     */
    private void addTransaction() {
        LocalDate date = readDate();

        String category = IO.readln("Ange kategori: ");
        if (category.isBlank()) {
            IO.println("Kategorin får inte vara tom.");
            return;
        }

        BigDecimal amount = readAmount();

        TransactionType type = amount.signum() < 0
                ? TransactionType.EXPENSE : TransactionType.INCOME;
        try {
            repository.add(new Transaction(date, category, amount, type));
        } catch (InvalidTransactionException e) {
            IO.println(e.getMessage());
        }
    }

    /**
     * Metod för att läsa in datumet som användaren matar in via den interaktiva menyn.
     * Returnerar LocalDate som används för att skapa en ny Transaction.
     */
    private LocalDate readDate() {
        LocalDate date;
        while (true) {
            try {
                return LocalDate.parse(IO.readln("Ange datum på format YYYY-MM-DD: "));
            } catch (DateTimeException e) {
                IO.println("Ogiltigt datum-format.");
            }
        }
    }

    /**
     * Metod för att läsa in belopp som användaren matar in via den interaktiva menyn. 0 är ogiltigt belopp.
     * Returnerar BigDecimal som används för att skapa en ny Transaction.
     */
    private BigDecimal readAmount() {
        while (true) {
            try {
                BigDecimal amount = new BigDecimal(
                        IO.readln("Ange belopp (ören anges efter punkt, exempelvis -130.49): "));
                if (amount.signum() == 0) {
                    IO.println("Beloppet kan inte vara 0.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                IO.println("Ogiltigt belopp.");
            }
        }
    }
}

