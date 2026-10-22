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
