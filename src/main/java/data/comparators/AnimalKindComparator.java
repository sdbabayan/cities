package data.comparators;

import data.model.Animal;

import java.util.Comparator;


public class AnimalKindComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.getKind().compareToIgnoreCase(o2.getKind());
    }
}
