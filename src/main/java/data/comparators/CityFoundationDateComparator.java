package data.comparators;

import data.model.City;

import java.util.Comparator;

public class CityFoundationDateComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getFoundationDate().isAfter(o2.getFoundationDate())) {
            return 1;
        } else if (o1.getFoundationDate().isBefore(o2.getFoundationDate())) {
            return -1;
        } else return 0;
    }
}
