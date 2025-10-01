package examples.domain.interfaces;

import examples.data.repository.MyArrayList;

import java.util.ArrayList;
import java.util.Comparator;

public interface SearchStrategy<T> {

    boolean search(MyArrayList<T> list, T object, Comparator<? super T> comparator);
}
