import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> transactions = new ArrayList<>();


    /**
     * Metod för att addera en transaktion (inkomst eller utgift). Tar input av typen T, men lägger bara till
     * i listan av typen record Transaction, är input av annan typ skrivs ett meddelande ut
     */
    public void add(T item) {
        if (item == null)
            IO.println("Du har försökt lägga till null.");
        else if (item instanceof Transaction tr) {
            transactions.add(item);
            IO.println("Din transaktion har lagts till.");
        }
        else
            IO.println("Din transaktion lades inte till. Kontakta kundtjänst.");
    }

    /**
     * Metod för att hitta och sammanställa alla transaktioner i en lista. Metoden returnerar en kopia av en lista
     * för att förhindra otillåten/oförutsedda ändringar.
     */
    public List<T> findAll() {
        return new ArrayList<>(transactions);
    }

    /**
     * Metod för att kunna filtrera i transaktionslistan. Returnerar en lista.
     */
    public List <T> findWhere(Predicate<T> condition) {
        List <T> filteredResult = new ArrayList<>();
        for (T item : transactions) {
            if (condition.test(item))
                filteredResult.add(item);
        }
        return filteredResult;
    }




}
