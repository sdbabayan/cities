package examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface SortStrategy<T> {
    MyArrayList<T> sort(MyArrayList<T> array, Comparator<T> comparator);
}

class BubbleSortStrategy<T> implements SortStrategy<T> {
    public MyArrayList<T> sort(MyArrayList<T> array, Comparator<T> comparator) {
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

class QuickSortStrategy<T> implements SortStrategy<T> {
    public MyArrayList<T> sort(MyArrayList<T> array, Comparator<T> comparator) {
        return quickSort(array, 0, array.size() - 1, comparator);
    }

    MyArrayList<T> quickSort(MyArrayList<T> array, int leftBorder, int rightBorder, Comparator<T> comparator) {
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

class MyArrayList<T> extends ArrayList<T> {
    public MyArrayList<T> sortByStrategy(SortStrategy<T> sortStrategy, Comparator<T> comparator) {
        System.out.printf("Сортировка коллекции в соответствии со стратегией %s с компаратором %s:\n",
                sortStrategy.getClass().getSimpleName(), comparator.getClass().getSimpleName());
        return sortStrategy.sort(this, comparator);
    }
}

class FromSmallToBigComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}

class FromBigToSmallComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o2 - o1;
    }
}

public class Strategy {
    public static void main(String[] args) {
        MyArrayList<Integer> list = Stream.of(50, 21, 41, 72, 12, 0, 92)
                .collect(Collectors.toCollection(MyArrayList::new));

        System.out.println(list.sortByStrategy(new BubbleSortStrategy<>(), new FromSmallToBigComparator()));
        System.out.println(list.sortByStrategy(new BubbleSortStrategy<>(), new FromBigToSmallComparator()));
        System.out.println(list.sortByStrategy(new QuickSortStrategy<>(), new FromSmallToBigComparator()));
        System.out.println(list.sortByStrategy(new QuickSortStrategy<>(), new FromBigToSmallComparator()));
    }
}