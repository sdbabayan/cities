package data.comparators;

import data.model.Person;
import domain.interfaces.IntValueReturnable;

import java.util.Comparator;


public class IntValueComparator<T extends IntValueReturnable> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return o1.getIntValue() - o2.getIntValue();
    }
}

