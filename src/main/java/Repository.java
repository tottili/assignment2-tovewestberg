import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {

    //TODO! Metod för att addera en transaktion (inkomst eller utgift) med validering av indatan. Tar input av typen T
    public void add(T item) {

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
