package data.comparators;

import data.model.Animal;

import java.util.Comparator;


public class AnimalEyesColorComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.getEyesColor().compareTo(o2.getEyesColor());
    }
}
