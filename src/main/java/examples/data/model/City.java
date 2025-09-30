package examples.data.model;

public class City {
    private String cityName;
    private String regionName;
    private Location location;
    private HistoricalDate foundationDate;
    private int populationQty;

    public String getCityName() {
        return cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public Location getLocation() {
        return location;
    }

    public HistoricalDate getFoundationDate() {
        return foundationDate;
    }

    public int getPopulationQty() {
        return populationQty;
    }

    private City() {
    }

    public static class Builder {
        City city;

        Builder() {
            city = new City();
        }

        public Builder setCityName(String cityName) {
            city.cityName = cityName;
            return this;
        }

        public Builder setRegionName (String regionName) {
            city.regionName = regionName;
            return this;
        }

        public Builder setLocation(Location location) {
            city.location = location;
            return this;
        }

        public Builder setFoundationDate(HistoricalDate foundationDate) {
            city.foundationDate = foundationDate;
            return this;
        }

        public Builder setPopulationQty(int populationQty) {
            city.populationQty = populationQty;
            return this;
        }

        public City build() {
            return city;
        }
    }
}

class Location {
    private final double latitude;
    private final double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location(double latitude, double longitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new LocationException("Широта должна быть от -90 до 90 градусов");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new LocationException("Долгота должна быть от -180 до 180 градусов");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getDistance(Location destinationPoint) {
        final double R = 6371.0;

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(destinationPoint.getLatitude());
        double lon2 = Math.toRadians(destinationPoint.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;
    }

    @Override
    public String toString() {
        return ("широта: " + String.valueOf(this.latitude) + ", долгота: " + String.valueOf(this.longitude));
    }
}

class LocationException extends RuntimeException {
    public LocationException(String message) {
        super(message);
    }
}

class HistoricalDate {
    private int day;
    private int month;
    private int year;

    public HistoricalDate(String date) {
    }
}

