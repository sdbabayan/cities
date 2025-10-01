package examples;

import examples.data.model.City;
import examples.data.model.HistoricalDate;
import examples.data.model.Location;
import examples.data.model.LocationException;

class Program {
    public static void main(String[] args) {
        try {
            Location loc = new Location(3.5, 360.0);
        } catch (LocationException e) {
            System.out.println(e.getMessage());
        }
        try {
            City city = new City.Builder()
                    .setCityName("Санкт-Петербург")
                    .setRegionName("Санкт-Петербург")
                    .setLocation(new Location(59.938784, 30.314997))
                    .setFoundationDate(new HistoricalDate("01.05.1950"))
                    .setPopulationQty(10_000_000).build();
            System.out.printf("%s; регион: %s; местоположение: %s; дата основания: %s; численность населения: %s\n",
                    city.getCityName(), city.getRegionName(), city.getLocation().toString(),
                    city.getFoundationDate(), city.getPopulationQty());
            System.out.println(city.getLocation().getDistance(new Location(-23.757778, -60.795938)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
