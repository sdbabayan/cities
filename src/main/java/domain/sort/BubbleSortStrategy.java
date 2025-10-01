package domain.sort;


import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.SortStrategy;

import java.util.Comparator;

class BubbleSortStrategy<T> implements SortStrategy<T> {
    public ArrayListToSortByStrategy<T> sort(ArrayListToSortByStrategy<T> array, Comparator<T> comparator) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.size(); i++) {
                if (comparator.compare(array.get(i), array.get(i - 1)) < 0) {
                    T tmp = array.get(i);
                    array.set(i, array.get(i - 1));
                    array.set(i - 1, tmp);
                    needIteration = true;
                }
            }
        }
        return array;
    }
}
