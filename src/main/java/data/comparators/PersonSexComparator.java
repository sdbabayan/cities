package data.comparators;

import data.model.Person;

import java.util.Comparator;

public class PersonSexComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getSex().compareTo(o2.getSex());
    }
}
