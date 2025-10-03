package domain.sort;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.SortStrategy;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {
    public ArrayListToSortByStrategy<T> sort(ArrayListToSortByStrategy<T> list, Comparator<T> comparator) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < list.size(); i++) {
                if (comparator.compare(list.get(i), list.get(i - 1)) < 0) {
                    T tmp = list.get(i);
                    list.set(i, list.get(i - 1));
                    list.set(i - 1, tmp);
                    needIteration = true;
                }
            }
        }
        return list;
    }
}
