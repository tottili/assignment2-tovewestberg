import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {
    private Repository<Transaction> repo = new Repository<>();

    @Test
    void addPutsIncomeInTheTransactionList() {
        Transaction income = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000.00), BudgetApplication.TransactionType.INCOME);

        repo.add(income);
        var result = repo.findAll();

        assertEquals(List.of(income), result);
    }

    @Test
    void addPutsExpenseInTheTransactionList() {
        Transaction expense = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);

        repo.add(expense);
        var result = repo.findAll();

        assertEquals(List.of(expense), result);
    }

    @Test
    void addIgnoresNullTransactions() {
        Transaction income = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000.00), BudgetApplication.TransactionType.INCOME);

        repo.add(income);
        repo.add(null);
        var result = repo.findAll();

        assertEquals(List.of(income), result);
    }


}