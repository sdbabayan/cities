package examples.domain.interfaces;


import examples.data.repository.MyArrayList;

import java.util.Comparator;

public interface SortStrategy<T> {
    MyArrayList<T> sort(MyArrayList<T> array, Comparator<T> comparator);
}
