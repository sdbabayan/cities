package examples.domain.search;

import examples.domain.interfaces.SearchStrategy;
import java.util.ArrayList;
import java.util.Comparator;

public class BinarySearch<T> implements SearchStrategy<T> {

    @Override
    public boolean search(ArrayList<T> list, T object, Comparator<? super T> comparator) {
        if (list == null || object == null || comparator == null) {
            throw new IllegalArgumentException("List, object and comparator cannot be null");
        }

        if (list.isEmpty()) {
            return false;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            T midElement = list.get(mid);
            int comparison = comparator.compare(midElement, object);

            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
}