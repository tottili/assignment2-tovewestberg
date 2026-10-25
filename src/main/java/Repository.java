import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> list = new ArrayList<>();


    /**
     * Metod för att addera till en lista. Tar input av typen T.
     */
    public void add(T item) {
        if (item == null)
            IO.println("Du har försökt lägga till ett ogiltigt värde.");
        else
            list.add(item);
    }

    /**
     * Metod för att hitta och sammanställa alla element i en lista. Metoden returnerar en kopia av en lista
     * för att förhindra otillåten/oförutsedda ändringar.
     */
    public List<T> findAll() {
        return new ArrayList<>(list);
    }

    /**
     * Metod för att kunna filtrera i en lista. Returnerar den filtrerade listan.
     */
    public List <T> findWhere(Predicate<T> condition) {
        List <T> filteredList = list.stream().filter(condition).toList();
        return filteredList;
    }
}
