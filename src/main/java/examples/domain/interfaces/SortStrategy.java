package examples.domain.interfaces;


import examples.data.repository.ArrayListToSortByStrategy;

import java.util.Comparator;

public interface SortStrategy<T> {
    ArrayListToSortByStrategy<T> sort(ArrayListToSortByStrategy<T> list, Comparator<T> comparator);
}