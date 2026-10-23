import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculationsTest {
    private Calculations calc = new Calculations();
    private Repository<Transaction> repo = new Repository<>();


    @Test
    void calculatesBalanceCorrectlyWhenIncomeGreaterThanExpenses() {
        Transaction salary = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000), BudgetApplication.TransactionType.INCOME);
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        repo.add(salary);
        repo.add(rent);

        var result = calc.calculateTotalBalance(repo.findAll());

        assertEquals(BigDecimal.valueOf(22167), result);
    }

    @Test
    void givesNegativeBalanceWhenExpenseGreaterThanIncome() {
        Transaction salary = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(7500), BudgetApplication.TransactionType.INCOME);
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        repo.add(salary);
        repo.add(rent);

        var result = calc.calculateTotalBalance(repo.findAll());

        assertEquals(BigDecimal.valueOf(-2333), result);
    }
}