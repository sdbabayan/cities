package domain.sort;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.IntValueReturnable;
import domain.interfaces.SortEvenStrategy;
import domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenSortStrategy implements SortEvenStrategy {
    @Override
    public <T extends IntValueReturnable> void sortEven(ArrayListToSortByStrategy<T> list, SortStrategy sortStrategy, Comparator<T> comparator) {
        ArrayListToSortByStrategy<T> evens = new ArrayListToSortByStrategy<>();
        for (T obj : list) {
            if (obj.getIntValue() % 2 == 0) {
                evens.add(obj);
            }
        }

        evens.sortByStrategy(sortStrategy, comparator);

        int idx = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIntValue() % 2 == 0) {
                list.set(i, evens.get(idx++));
            }
        }
        list.forEach(System.out::println);
    }
}
