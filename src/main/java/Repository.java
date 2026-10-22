import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> transactions = new ArrayList<>();


    /**
     * Metod för att addera en transaktion (inkomst eller utgift). Tar input av typen T.
     */
    public void add(T item) {
        if (item == null)
            IO.println("Du har försökt lägga till null.");
        else
            transactions.add(item);
    }

    /**
     * Metod för att hitta och sammanställa alla transaktioner i en lista. Metoden returnerar en kopia av en lista
     * för att förhindra otillåten/oförutsedda ändringar.
     */
    public List<T> findAll() {
        return new ArrayList<>(transactions);
    }

    /**
     * Metod för att kunna filtrera i transaktionslistan. Returnerar en icke-ändringsbar lista.
     */
    public List <T> findWhere(Predicate<T> condition) {
        List <T> filteredList;
        filteredList = transactions.stream().filter(condition).toList();
        return filteredList;
    }
}
