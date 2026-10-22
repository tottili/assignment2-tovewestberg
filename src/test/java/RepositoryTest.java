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
        Transaction income = new Transaction(LocalDate.of(2026, 10, 10), "Lön",
                BigDecimal.valueOf(32000.00), BudgetApplication.TransactionType.INCOME);

        repo.add(income);
        repo.add(null);
        var result = repo.findAll();

        assertEquals(List.of(income), result);
    }

    @Test
    void findAllReturnsEmptyListWhenRepositoryIsEmpty() {
        //Arrange-delen är deklarerad under klassnamnet, skapa ett tomt repository.

        var result = repo.findAll();

        assertEquals(List.of(), result);
    }

    @Test
    void findAllReturnsACopyOfTheTransactionList() {
        Transaction income = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000.00), BudgetApplication.TransactionType.INCOME);

        repo.add(income);
        var result = repo.findAll();
        IO.println(result);
        result.clear();
        IO.println(result);

        assertEquals(List.of(income), repo.findAll());
    }

    @Test
    void findWhereReturnsCorrectFilteringByExpense() {
        Transaction salary = new Transaction(LocalDate.of(2026,10,10), "Lön",
                BigDecimal.valueOf(32000.00), BudgetApplication.TransactionType.INCOME);
        Transaction rent = new Transaction(LocalDate.of(2026, 10, 10), "Hyra",
                BigDecimal.valueOf(-9833), BudgetApplication.TransactionType.EXPENSE);
        Transaction carPayment = new Transaction(LocalDate.of(2026, 10, 18),
                "Avbetalning bil", BigDecimal.valueOf(-4228), BudgetApplication.TransactionType.EXPENSE);
        Transaction rentersInsurance = new Transaction(LocalDate.of(2026, 10, 18), "Hemförsäkring",
                BigDecimal.valueOf(-249), BudgetApplication.TransactionType.EXPENSE);

        repo.add(salary);
        repo.add(rent);
        repo.add(carPayment);
        repo.add(rentersInsurance);

        var result = repo.findWhere(transaction ->
                transaction.incomeOrExpense() == BudgetApplication.TransactionType.EXPENSE);

        assertEquals(List.of(rent, carPayment, rentersInsurance), result);
    }
}