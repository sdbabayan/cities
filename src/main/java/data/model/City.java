package data.model;

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

        public Builder() {
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

