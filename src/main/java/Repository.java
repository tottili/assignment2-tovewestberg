import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> transactions = new ArrayList<>();


    //Metod för att addera en transaktion (inkomst eller utgift) med validering av indatan. Tar input av typen T
    //TODO! Anpassa metoden efter konstruktorn som nu utför validering.
    public void add(T item) {
        if (item == null || (item instanceof Transaction tr && tr.amount().signum() == 0))
           IO.println("Du har försökt lägga till en transaktion som inte är giltig.");
        else if (item instanceof Transaction tr
                    && tr.amount().signum() == 1
                    && tr.incomeOrExpense() == BudgetApplication.TransactionType.INCOME) {
            transactions.add(item);
        } else if (item instanceof Transaction tr
                    && tr.amount().signum() == -1
                    && tr.incomeOrExpense() == BudgetApplication.TransactionType.EXPENSE) {
            transactions.add(item);
        } else {
            IO.println("Din transaktion lades inte till. Kontakta kundtjänst.");
        }
    }

    //TODO! Metod för att hämta alla transaktioner, returnerar en List av typen T.
    public List<T> findAll() {
        return null;
    }

    //TODO! Metod för att filtrera bland transaktionerna och hämta transaktioner beroende på vårt urval. Returnerar
    // en List av typen T.
    public List <T> findWhere(Predicate<T> villkor) {
        return null;
    }




}
