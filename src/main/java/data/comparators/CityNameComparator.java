package data.comparators;

import data.model.City;

import java.util.Comparator;

public class CityNameComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        return o1.getCityName().compareToIgnoreCase(o2.getCityName());
    }
}