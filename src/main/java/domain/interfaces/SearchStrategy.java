package domain.interfaces;

import data.repository.ArrayListToSortByStrategy;

import java.util.Comparator;

public interface SearchStrategy<T> {

    boolean search(ArrayListToSortByStrategy<T> list, T object, Comparator<? super T> comparator);
}
