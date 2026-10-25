import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(LocalDate date, String category, BigDecimal amount, TransactionType incomeOrExpense) {

    /** Kompakt konstruktor med kontroll av inparametrar för att säkerställa att transaktionerna
     *  är giltiga innan de skapas.
     */
    public Transaction {

        if (date == null)
            throw new InvalidTransactionException("Inte giltigt datum");

        if (category == null || category.isBlank())
            throw new InvalidTransactionException("Kategorin får inte vara tom.");

        if (amount == null || amount.signum() == 0)
            throw new InvalidTransactionException("Beloppet kan inte vara 0.");

        if (incomeOrExpense == null)
            throw new InvalidTransactionException("Transaktionen måste märkas.");
        else if (incomeOrExpense == TransactionType.INCOME
                && amount.signum() == -1)
            throw new InvalidTransactionException("Inkomster måste vara positiva belopp.");
        else if (incomeOrExpense == TransactionType.EXPENSE
                && amount.signum() == 1)
            throw new InvalidTransactionException("Utgifter måste vara negativa belopp.");
    }

    /**
     *  Enum för att avgöra de två utgiftstyper som finns.
     */
    public enum TransactionType {
        INCOME,
        EXPENSE
    }
}