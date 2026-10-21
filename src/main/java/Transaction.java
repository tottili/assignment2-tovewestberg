import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Transaction(LocalDate date, String category, BigDecimal amount,
                          BudgetApplication.TransactionType incomeOrExpense) {

    // Kompakt konstruktor med kontroll av inparametrar för att säkerställa att transaktionerna
    // är giltiga innan de skapas.
    public Transaction {
        Objects.requireNonNull(date, "Inte giltigt datum.");
        Objects.requireNonNull(category, "Inte giltig kategori.");
        Objects.requireNonNull(amount, "Inte giltigt belopp.");
        Objects.requireNonNull(incomeOrExpense, "Inte giltig märkning (inkomst/utgift)");

        if (category.isBlank())
            throw new IllegalArgumentException("Kategorin får inte vara tom.");

        if (amount.signum() == 0)
            throw new IllegalArgumentException("Beloppet kan inte vara 0.");

        if (incomeOrExpense == BudgetApplication.TransactionType.INCOME
                && amount.signum() == -1)
            throw new IllegalArgumentException("Inkomster måste vara positiva belopp.");

        if (incomeOrExpense == BudgetApplication.TransactionType.EXPENSE
                && amount.signum() == 1)
            throw new IllegalArgumentException("Utgifter måste vara negativa belopp.");
    }
}