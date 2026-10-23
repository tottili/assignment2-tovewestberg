import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @Test
    void givesZeroBalanceWhenIncomeAndExpenseSameAmount() {
        Transaction salary = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(9833), BudgetApplication.TransactionType.INCOME);
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        repo.add(salary);
        repo.add(rent);

        var result = calc.calculateTotalBalance(repo.findAll());

        assertEquals(BigDecimal.valueOf(0), result);
    }

    @Test
    void givesZeroBalanceWhenThereAreNoTransactions() {
        //Arrange-delen ligger i detta fall under klassnamnet.

        var result = calc.calculateTotalBalance(repo.findAll());

        assertEquals(BigDecimal.valueOf(0), result);
    }

    @Test
    void expensesAreMadePositiveAmountsForPresentingExpensesSummary() {
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        Transaction carPayment = new Transaction(LocalDate.of(2026, 10, 18),
                "Bilkostnader", BigDecimal.valueOf(-4228), BudgetApplication.TransactionType.EXPENSE);

        var transactions = List.of(rent, carPayment);
        var result = calc.calculateExpensesPerCategory(transactions);

        assertEquals(Map.of("Hyra", BigDecimal.valueOf(9833),
                "Bilkostnader", BigDecimal.valueOf(4228)), result);
    }

    @Test
    void summationOfExpensesExcludesIncome() {
        Transaction salary = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000), BudgetApplication.TransactionType.INCOME);
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);

        var transactions = List.of(salary, rent);
        var result = calc.calculateExpensesPerCategory(transactions);

        assertEquals(Map.of("Hyra", BigDecimal.valueOf(9833)), result);
    }

    @Test
    void expensesAreGroupedByCategory() {
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        Transaction carPayment = new Transaction(LocalDate.of(2026, 10, 18),
                "Bilkostnader", BigDecimal.valueOf(-4228), BudgetApplication.TransactionType.EXPENSE);
        Transaction carInsurance = new Transaction(LocalDate.of(2026, 10, 18),
                "Bilkostnader", BigDecimal.valueOf(-249), BudgetApplication.TransactionType.EXPENSE);

        var transactions = List.of(rent, carPayment, carInsurance);
        var result = calc.calculateExpensesPerCategory(transactions);

        assertEquals(Map.of("Hyra", BigDecimal.valueOf(9833), "Bilkostnader", BigDecimal.valueOf(4477)), result);
    }
}