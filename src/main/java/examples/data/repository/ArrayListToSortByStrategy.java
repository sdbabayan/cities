package examples.data.repository;

import examples.domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ArrayListToSortByStrategy<T> extends ArrayList<T> {
    public ArrayListToSortByStrategy() {
        super();
    }

    public ArrayListToSortByStrategy(Collection<? extends T> c) {
        super(c);
    }

    public ArrayListToSortByStrategy<T> sortByStrategy(SortStrategy<T> sortStrategy, Comparator<T> comparator) {
        System.out.printf("Сортировка коллекции в соответствии со стратегией %s с компаратором %s:\n",
                sortStrategy.getClass().getSimpleName(), comparator.getClass().getSimpleName());
        return sortStrategy.sort(this, comparator);
    }
}
