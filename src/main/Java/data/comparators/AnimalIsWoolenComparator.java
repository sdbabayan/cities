package data.comparators;

import data.model.Animal;

import java.util.Comparator;

public class AnimalIsWoolenComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        return Boolean.compare(o1.getIsWoolen(), o2.getIsWoolen());
    }
}
