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

    public ArrayListToSortByStrategy<T> sortByStrategy(SortStrategy sortStrategy, Comparator<T> comparator) {
        ArrayListToSortByStrategy sorted = sortStrategy.sort(this, comparator);
        this.clear();
        this.addAll(sorted);
        return this;
    }

    public T searchByStrategy(SearchStrategy searchStrategy, T keyObject, Comparator<T> comparator) {
        return (T) searchStrategy.search(this, keyObject, comparator);
    }
}
