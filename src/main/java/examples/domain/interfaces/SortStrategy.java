package examples.domain.interfaces;

import examples.MyArrayList;

import java.util.Comparator;

interface SortStrategy<T> {
    MyArrayList<T> sort(MyArrayList<T> array, Comparator<T> comparator);
}
