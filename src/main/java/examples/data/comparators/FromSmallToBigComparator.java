package examples.data.comparators;

import java.util.Comparator;

class FromSmallToBigComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}
