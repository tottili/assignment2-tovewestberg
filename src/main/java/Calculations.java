import java.math.BigDecimal;
import java.util.List;

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

    //TODO! Metod för att beräkna utgifter per kategori.
    public void calculateExpensesPerCategory() {

    }
}
