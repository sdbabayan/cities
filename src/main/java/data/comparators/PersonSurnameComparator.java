package data.comparators;

import java.util.Comparator;

import data.model.Person;


public class PersonSurnameComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getSurname().compareToIgnoreCase(o2.getSurname());
    }
}

