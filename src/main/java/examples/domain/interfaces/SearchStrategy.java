package examples.domain.interfaces;

import examples.data.repository.ArrayListToSortByStrategy;

import java.util.ArrayList;
import java.util.Comparator;

public interface SearchStrategy<T> {

    T search(ArrayListToSortByStrategy<T> list, T object, Comparator<? super T> comparator);
}
