import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

public class BudgetApplication {
    private final Repository<Transaction> repository = new Repository<>();

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
        readFromFile();

        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> addTransactionByUser();
                case "2" -> IO.println("Visa alla transaktioner");
                case "3" -> IO.println("Visa saldo och sammanställning per kategori");
                case "4" -> IO.println("Filtrera transaktioner");
                case "5" -> saveToFile();
                case "e", "E" -> running = false;
                default -> IO.println("Det går inte att välja, försök igen.");
            }
        } while (running);
    }

    /**
     * Metod skapad för att hantera användarens inmatning i Budgethanteraren. Tar användarens input,
     * gör validering av indatan innan metoden sen skickar vidare en new Transaction till Repository<T>.
     */
    private void addTransactionByUser() {
        LocalDate date = readDate();

        String category = IO.readln("Ange kategori: ");
        if (category.isBlank()) {
            IO.println("Kategorin får inte vara tom.");
            return;
        }

        BigDecimal amount = readAmount();

        Transaction.TransactionType type = amount.signum() < 0
                ? Transaction.TransactionType.EXPENSE : Transaction.TransactionType.INCOME;
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

    /**
     * Hjälpmetod för att hitta path-en till filen Budget. (csv-format)
     * Returnerar en Path.
     */
    private Path getBudgetFilePath() {
        String homeFolder = System.getProperty("user.home");
        Path homePath = Path.of(homeFolder);
        return homePath.resolve("Budget.csv");
    }

    private void loadBudgetFile(List<String> budgetLines) {
        for (int i = 0; i < budgetLines.size(); i++) {
            String[] fields = budgetLines.get(i).split(",", -1);

            if (fields.length != 4) {
                IO.println("Hoppar över rad " + (i + 1) + ": det måste vara fyra kolumner");
                continue;
            }

            try {
                Transaction transaction = new Transaction(LocalDate.parse(fields[0]), fields[1],
                        new BigDecimal(fields[2]), Transaction.TransactionType.valueOf(fields[3]));
                repository.add(transaction);
            } catch (DateTimeException | IllegalArgumentException | InvalidTransactionException e) {
                IO.println("Hoppar över rad " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    /**
     * Metod för att läsa in transaktioner från en fil, ifall den finns.
     */
    private void readFromFile() {
        Path path = getBudgetFilePath();

        try {
            loadBudgetFile(Files.readAllLines(path));
        } catch (NoSuchFileException e) {
            IO.println("Ingen sparad fil hittades " + e.getMessage());
        } catch (IOException e) {
            IO.println("Det blev ett fel när filen skulle läsas in " + e.getMessage());
        }
    }

    /**
     * Metod för att spara tillagda transaktioner i en fil med namn Budget (.csv).
     */
    private void saveToFile() {
        Path path = getBudgetFilePath();

        List<String> budgetLines = repository.findAll().stream()
                        .map(transaction -> String.join(",",
                                transaction.date().toString(),
                                transaction.category(),
                                transaction.amount().toPlainString(),
                                transaction.incomeOrExpense().name())).toList();

        try {
            Files.write(path, budgetLines);
            IO.println("Filen är nu sparad.");
        } catch (IOException e) {
            IO.println("Det gick inte att spara filen. " + e.getMessage() + " Kontakta kundtjänst.");
        }
    }
}

