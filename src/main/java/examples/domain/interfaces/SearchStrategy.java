package examples.domain.interfaces;

import java.util.ArrayList;
import java.util.Comparator;

public interface SearchStrategy<T> {

    int search(ArrayList<T> list, T object, Comparator<? super T> comparator);
}
