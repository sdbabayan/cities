package domain.interfaces;

import data.repository.ArrayListToSortByStrategy;

import java.util.Comparator;

public interface SortEvenStrategy<T extends IntValueReturnable> {
   void sortEven(ArrayListToSortByStrategy<T> list, SortStrategy<T> sortStrategy, Comparator<T> comparator);
}
