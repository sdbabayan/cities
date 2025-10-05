package data.repository;

import domain.interfaces.SortStrategy;
import domain.interfaces.SearchStrategy;

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

    public ArrayListToSortByStrategy<T> sortByStrategy(SortStrategy sortStrategy, Comparator<T> comparator){
        return sortStrategy.sort(this, comparator);
    }

    public T searchByStrategy(SearchStrategy searchStrategy, T keyObject, Comparator<T> comparator) {
        return (T) searchStrategy.search(this, keyObject, comparator);
    }    
}
