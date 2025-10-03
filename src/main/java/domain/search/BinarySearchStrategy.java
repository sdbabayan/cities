package domain.search;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.SearchStrategy;

import java.util.Comparator;

public class BinarySearchStrategy implements SearchStrategy {
    @Override
    public <T> T search(ArrayListToSortByStrategy<T> list, T keyObject, Comparator<T> comparator) {
        return null;
    }
}
