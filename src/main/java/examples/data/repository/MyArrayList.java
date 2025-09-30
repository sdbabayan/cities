package examples.data.repository;

import examples.domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;

class MyArrayList<T> extends ArrayList<T> {
    public MyArrayList<T> sortByStrategy(SortStrategy<T> sortStrategy, Comparator<T> comparator) {
        System.out.printf("Сортировка коллекции в соответствии со стратегией %s с компаратором %s:\n",
                sortStrategy.getClass().getSimpleName(), comparator.getClass().getSimpleName());
        return sortStrategy.sort(this, comparator);
    }
}
