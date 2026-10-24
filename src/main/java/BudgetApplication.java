import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BudgetApplication {
    private final Repository<Transaction> repository = new Repository<>();
    private final Calculations calc = new Calculations();
    private static final Logger LOG = Logger.getLogger(BudgetApplication.class.getName());

    static void main() {
        new BudgetApplication().runBudgetApplication();
    }

    /**
     * Metod som visar den interaktiva menyn som används i budgethanteraren.
     */
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

    /**
     * Metod för att köra programmet (budgethanteraren) och börjar med att läsa in från filen Budget om den finns.
     */
    public void runBudgetApplication() {
        configureLogging(Level.INFO);
        LOG.info("Applikationen startar");
        readFromFile();

        boolean running = true;
        do {
            displayMenu();
            String choice = IO.readln("Gör ett val på menyn: ");
            switch (choice) {
                case "1" -> addTransactionByUser();
                case "2" -> displayAllTransactions();
                case "3" -> {
                                showTotalBalance();
                                showExpensePerCategory();
                }
                case "4" -> IO.println("Filtrera transaktioner");
                case "5" -> saveToFile();
                case "e", "E" -> running = false;
                default -> IO.println("Det går inte att välja, försök igen.");
            }
        } while (running);
        LOG.info("Applikationen stängs ner.");
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
            String[] field = budgetLines.get(i).split(",", -1);

            if (field.length != 4) {
                LOG.log(Level.WARNING, "Rad " + (i + 1) + " har fel antal kolumner och skippas vid inläsning till lista");
                IO.println("Hoppar över rad " + (i + 1) + ": det måste vara fyra kolumner");
                continue;
            }

            try {
                Transaction transaction = new Transaction(LocalDate.parse(field[0]), field[1],
                        new BigDecimal(field[2]), Transaction.TransactionType.valueOf(field[3]));
                repository.add(transaction);
            } catch (DateTimeException | IllegalArgumentException | InvalidTransactionException e) {
                LOG.log(Level.WARNING, "Felaktig filrad " + (i + 1) + " kommer skippas vid inläsning till lista");
                IO.println("Hoppar över rad " + (i + 1) + ": " + e.getMessage());
            }
        }
        LOG.info(repository.findAll().size() + " antal transaktioner överförda från fil.");
    }

    /**
     * Metod för att läsa in transaktioner från en fil, ifall den finns.
     */
    private void readFromFile() {
        Path path = getBudgetFilePath();

        try {
            LOG.info("Försöker ladda transaktioner från fil " + path.getFileName());
            loadBudgetFile(Files.readAllLines(path));
            LOG.info("Transaktioner klara.");
        } catch (NoSuchFileException e) {
            LOG.log(Level.WARNING, "Ingen fil hittades", e);
            IO.println("Ingen sparad fil hittades " + e.getMessage());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Kunde inte läsa in filen. ", e);
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
            LOG.info("Försöker spara filen: " + path.getFileName());
            Files.write(path, budgetLines);
            IO.println("Filen är nu sparad.");
            LOG.info("Fil " + path.getFileName() + " sparad");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Det gick inte att spara fil " + path.getFileName(), e);
            IO.println("Det gick inte att spara filen. " + e.getMessage() + " Kontakta kundtjänst.");
        }
    }

    /**
     * Metod som med Level som inparameter för att kunna anpassa loggningsmeddelandena, konfigurerar även
     * filen som ska spara loggningen.
     */
    private void configureLogging(Level level) {
        LOG.setLevel(level);
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOG.addHandler(fileHandler);
            LOG.setUseParentHandlers(false);
        } catch (IOException e) {
            IO.println("Kunde inte konfigurera loggning" + e.getMessage());
        }
    }

    /**
     * Hjälpmetod för att skriva ut alla transaktioner i listan.
     */
    private void displayAllTransactions() {
        IO.println(repository.findAll());
    }

    /**
     * Hjälpmetod för att skriva ut alla det totala saldot med hänsyn till transaktionerna i listan.
     */
    private void showTotalBalance() {
        IO.println("Saldo: " + calc.calculateTotalBalance(repository.findAll()));
    }

    /**
     * Hjälpmetod för att sammanställa alla utgifter, grupperat per kategori.
     */
    private void showExpensePerCategory() {
        IO.println("Sammanställning per kategori: " + calc.calculateExpensesPerCategory(repository.findAll()));
    }
}

