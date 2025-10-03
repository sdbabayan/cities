package domain.interfaces;

import data.repository.ArrayListToSortByStrategy;

import java.util.Comparator;

public interface SearchStrategy<T> {

    T search(ArrayListToSortByStrategy<T> list, T object, Comparator<? super T> comparator);
}
