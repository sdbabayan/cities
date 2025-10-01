package data.comparators;

import data.model.City;

import java.util.Comparator;

public class CityDistanceComparator implements Comparator<City> {
    private double comparedCityLatitude;
    private double comparedCityLongitude;

    public CityDistanceComparator(double  comparedCityLatitude, double comparedCityLongitude) {
        this.comparedCityLatitude = comparedCityLatitude;
        this.comparedCityLongitude = comparedCityLongitude;
    }

    @Override
    public int compare(City o1, City o2) {
        double distance1 = o1.getDistance(this.comparedCityLatitude, this.comparedCityLongitude);
        double distance2 = o2.getDistance(this.comparedCityLatitude, this.comparedCityLongitude);
        if (distance1 > distance2) {
            return 1;
        } else if (distance1 < distance2) {
            return -1;
        } else {
            return 0;
        }
    }
}