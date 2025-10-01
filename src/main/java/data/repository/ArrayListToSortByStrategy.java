package data.repository;

import domain.interfaces.SearchStrategy;
import domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListToSortByStrategy<T> extends ArrayList<T> {
    public ArrayListToSortByStrategy<T> sortByStrategy(SortStrategy sortStrategy, Comparator<T> comparator){
        return sortStrategy.sort(this, comparator);
    }

    public T searchByStrategy(SearchStrategy searchStrategy, T keyObject, Comparator<T> comparator) {
        return searchStrategy.search(this, keyObject, comparator);
    }
}
