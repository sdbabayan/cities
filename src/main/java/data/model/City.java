package data.model;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.IntValueReturnable;
import net.datafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class City implements IntValueReturnable {
    private String name;
    private double latitude;
    private double longitude;
    private LocalDate foundationDate;

    private City() {
    }

    public String getCityName() {
        return name;
    }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public LocalDate getFoundationDate() { return foundationDate; }

    public static class Builder {
        City city;

        public Builder() {
            city = new City();
        }

        public Builder setName(String name) {
            city.name = name;
            return this;
        }

        public Builder setLatitude(double latitude) throws LocationException {
            if (latitude < -90.0 || latitude > 90.0) {
                throw new LocationException("Широта должна быть от -90 до 90 градусов");
            }
            city.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) throws LocationException {
            if (longitude < -180.0 || longitude > 180.0) {
                throw new LocationException("Долгота должна быть от -180 до 180 градусов");
            }
            city.longitude = longitude;
            return this;
        }

        public Builder setFoundationDate (LocalDate foundationDate) {
            city.foundationDate = foundationDate;
            return this;
        }

        public City build() {
            return city;
        }
    }

    public static ArrayListToSortByStrategy<City> loadDataFromFile(String pathToFile) {
        ArrayListToSortByStrategy<City> cities = new ArrayListToSortByStrategy<>();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            try {
                // формат строки: name;latitude;longitude;foundationDate
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String name = parts[0];
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    String[] partsOfDate = parts[3].split("\\.");
                    int day = Integer.parseInt(partsOfDate[0]);
                    int month = Integer.parseInt(partsOfDate[1]);
                    int year = Integer.parseInt(partsOfDate[2]);
                    LocalDate date = LocalDate.of(year, month, day);
                    City city = new City.Builder()
                            .setName(name)
                            .setLatitude(lat)
                            .setLongitude(lon)
                            .setFoundationDate(date)
                            .build();
                    cities.add(city);
                }
            } catch (LocationException e) {
                System.out.println(e.getMessage());
            }
        }
        return cities;
    }

    public static ArrayListToSortByStrategy<City> loadRandomData(int elementsQty) {
        ArrayListToSortByStrategy<City> cities = new ArrayListToSortByStrategy<>();
        Faker faker = new Faker(new Locale("ru", "RU"));
        for (int i = 0; i < elementsQty; i++) {
            try {
                String name = faker.address().cityName();
                double lat = faker.number().randomDouble(6, -90, 90);
                double lon = faker.number().randomDouble(6, -180, 180);
                LocalDate date = LocalDate.of(
                        faker.number().numberBetween(1000, 2023),
                        faker.number().numberBetween(1, 12),
                        faker.number().numberBetween(1, 28)
                );
                City city = new City.Builder()
                        .setName(name)
                        .setLatitude(lat)
                        .setLongitude(lon)
                        .setFoundationDate(date)
                        .build();
                cities.add(city);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return cities;
    }

    public static City createObjectManually(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        System.out.println("Введите название города:");
        String name = scanner.nextLine();

        System.out.println("Введите широту (от -90 до 90 градусов):");
        double lat = Double.parseDouble(scanner.nextLine());

        System.out.println("Введите долготу (от -180 до 180 градусов):");
        double lon = Double.parseDouble(scanner.nextLine());

        System.out.println("Введите дату основания (dd.MM.yyyy):");
        LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);
        try {
            City city = new City.Builder()
                    .setName(name)
                    .setLatitude(lat)
                    .setLongitude(lon)
                    .setFoundationDate(date)
                    .build();
            return city;
        } catch (LocationException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayListToSortByStrategy<City> loadDataManually(Scanner scanner)  {
        ArrayListToSortByStrategy<City> cities = new ArrayListToSortByStrategy<>();

        System.out.println("Введите количество городов:");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            try {
                City city = createObjectManually(scanner);
                cities.add(city);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        return cities;
    }

    public double getDistance(double latitude, double longitude) {
        final double R = 6371.0;

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(latitude);
        double lon2 = Math.toRadians(longitude);

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
    public int getIntValue() {
        return (int)latitude;
    }

    @Override
    public String toString() {
        return String.format("Город: %s; широта: %.6f; долгота: %.6f, год основания: %s",
                name,
                latitude,
                longitude,
                foundationDate.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, foundationDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        City other = (City) obj;
        return Double.compare(latitude, other.latitude) == 0 &&
                Double.compare(longitude, other.longitude) == 0 &&
                Objects.equals(name, other.name) &&
                Objects.equals(foundationDate, other.foundationDate);
    }
}

