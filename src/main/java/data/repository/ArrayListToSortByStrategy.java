package data.repository;

import domain.interfaces.SearchStrategy;
import domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class ArrayListToSortByStrategy<T> extends ArrayList<T> {
    public ArrayListToSortByStrategy() {
        super();
    }

    public ArrayListToSortByStrategy(Collection<? extends T> c) {
        super(c);
    }

    public ArrayListToSortByStrategy<T> sortByStrategy(SortStrategy<T> sortStrategy, Comparator<T> comparator) {
        ArrayListToSortByStrategy<T> sorted = sortStrategy.sort(this, comparator);
        ArrayListToSortByStrategy<T> sortedCopy = new ArrayListToSortByStrategy<>(sorted);
        this.clear();
        this.addAll(sortedCopy);
        return this;
    }

    public T searchByStrategy(SearchStrategy searchStrategy, T keyObject, Comparator<T> comparator) {
        return (T) searchStrategy.search(this, keyObject, comparator);
    }
}
