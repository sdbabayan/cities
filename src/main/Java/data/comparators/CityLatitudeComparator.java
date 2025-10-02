package data.comparators;

import java.util.Comparator;

import data.model.City;

public class CityLatitudeComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1.getLatitude() < o2.getLatitude()) {
            return 1;
        } else if ((o1.getLatitude() > o2.getLatitude())) {
            return -1;
        } else return 0;
    }
}
