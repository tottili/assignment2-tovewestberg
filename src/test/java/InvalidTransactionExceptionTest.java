import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTransactionExceptionTest {

    @Test
    void throwsForCreatingTransactionWithNullDate() {
        String category = "Mat";
        BigDecimal amount = BigDecimal.valueOf(-326);
        var type = BudgetApplication.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(null, category, amount, type));
    }

    @Test
    void nullCategoryThrowsWhenCreatingTransaction() {
        LocalDate date = LocalDate.of(2026, 10, 18);
        BigDecimal amount = BigDecimal.valueOf(-326);
        var type = BudgetApplication.TransactionType.EXPENSE;

        assertThrows(InvalidTransactionException.class, () -> new Transaction(date, null, amount, type));
    }


}