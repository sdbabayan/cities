package data.comparators;

import data.model.City;

import java.util.Comparator;

public class CityLongitudeComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getLongitude() > o2.getLongitude()) {
            return 1;
        } else if ((o1.getLongitude() < o2.getLongitude())) {
            return -1;
        } else return 0;
    }
}