package examples.domain.sort;


import examples.data.repository.ArrayListToSortByStrategy;
import examples.domain.interfaces.SortStrategy;

import java.util.Comparator;

class QuickSortStrategy<T> implements SortStrategy<T> {
    public ArrayListToSortByStrategy<T> sort(ArrayListToSortByStrategy<T> array, Comparator<T> comparator) {
        return quickSort(array, 0, array.size() - 1, comparator);
    }

    ArrayListToSortByStrategy<T> quickSort(ArrayListToSortByStrategy<T> array, int leftBorder, int rightBorder, Comparator<T> comparator) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        T pivot = array.get((leftMarker + rightMarker) / 2);
        do {
            while (comparator.compare(array.get(leftMarker), pivot) < 0) {
                leftMarker++;
            }
            while (comparator.compare(array.get(rightMarker), pivot) > 0) {
                rightMarker--;
            }
            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    T tmp = array.get(leftMarker);
                    array.set(leftMarker, array.get(rightMarker));
                    array.set(rightMarker, tmp);
                }
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        if (leftMarker < rightBorder) {
            quickSort(array, leftMarker, rightBorder, comparator);
        }
        if (leftBorder < rightMarker) {
            quickSort(array, leftBorder, rightMarker, comparator);
        }
        return array;
    }
}
