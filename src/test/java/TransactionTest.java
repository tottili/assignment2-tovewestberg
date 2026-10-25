import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void throwsForCreatingTransactionWithNullDate() {
        String category = "Mat";
        BigDecimal amount = BigDecimal.valueOf(-326);
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(null, category, amount, type));
    }

    @Test
    void nullCategoryThrowsWhenCreatingTransaction() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        BigDecimal amount = BigDecimal.valueOf(-326);
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, null, amount, type));
    }

    @Test
    void amountNullThrowsWhenCreatingTransaction() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "Mat";
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, null, type));
    }

    @Test
    void enumNullThrowsWhenCreatingTransaction() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "Mat";
        BigDecimal amount = BigDecimal.valueOf(-326);

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, amount,
                null));
    }

    @Test
    void throwsForZeroAmount() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "Bredband";
        BigDecimal amount = BigDecimal.valueOf(0);
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, amount, type));
    }

    @Test
    void throwsForNegativeAmountWhenMarkedIncome() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "Bredband";
        BigDecimal amount = BigDecimal.valueOf(-479);
        var type = Transaction.TransactionType.INCOME;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, amount, type));
    }

    @Test
    void throwsForPositiveAmountWhenMarkedExpense() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "Bredband";
        BigDecimal amount = BigDecimal.valueOf(479);
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, amount, type));
    }

    @Test
    void throwsWhenCategoryIsBlank() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        String category = "";
        BigDecimal amount = BigDecimal.valueOf(479);
        var type = Transaction.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, category, amount, type));
    }
}