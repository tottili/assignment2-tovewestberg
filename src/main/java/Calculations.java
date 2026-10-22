import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Calculations {

    /**
     * Metod för att loopa igenom en lista med Transactions och summera saldot på kontot. Returnerar
     * en BigDecimal.
     */
    public BigDecimal calculateTotalBalance(List<Transaction> transactions) {
        BigDecimal totalBalance = BigDecimal.ZERO;

        for (Transaction tr : transactions) {
            totalBalance = totalBalance.add(tr.amount());
        }

        return totalBalance;
    }

    /**
     * Metod för att filtrera i transaktionslistan, skapar en stream och filtrerar fram kundens utgifter.
     * Grupperar transaktionerna enligt deras kategori. Alla belopp som fångats i strömmen är negativa, dessa
     * görs om till positiva belopp och sen summeras totalen. Returnerar en Map med transaktionernas kategori (String)
     * som nyckel och totala utgifter per kategori som värde (BigDecimal).
     */
    public Map<String, BigDecimal> calculateExpensesPerCategory(
            List<Transaction> transactions) {
        var expenses = transactions.stream()
                .filter(transaction ->
                        transaction.incomeOrExpense() == BudgetApplication.TransactionType.EXPENSE);

        var expensesPerCategory = expenses.collect(
                Collectors.groupingBy(Transaction::category,
                        Collectors.reducing(BigDecimal.ZERO,
                        transaction -> transaction.amount().negate(),
                                BigDecimal::add)));
        return expensesPerCategory;
    }
}
