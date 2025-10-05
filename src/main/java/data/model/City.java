package data.model;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.IntValueReturnable;
import net.datafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        public Builder setName(String name) throws IllegalArgumentException{
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Название города не может быть пустым");
            }
            if (!name.matches("[\\p{L} .'-]+")) {
                throw new IllegalArgumentException("Название города содержит недопустимые символы");
            }
            city.name = name.trim();
            return this;
        }

        public Builder setLatitude(double latitude) throws IllegalArgumentException {
            if (latitude < -90.0 || latitude > 90.0) {
                throw new IllegalArgumentException("Широта должна быть от -90 до 90 градусов");
            }
            city.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) throws IllegalArgumentException {
            if (longitude < -180.0 || longitude > 180.0) {
                throw new IllegalArgumentException("Долгота должна быть от -180 до 180 градусов");
            }
            city.longitude = longitude;
            return this;
        }

        public Builder setFoundationDate (LocalDate foundationDate) throws IllegalArgumentException{
            if (foundationDate == null) {
                throw new IllegalArgumentException("Дата основания не может быть пустой");
            }
            if (foundationDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Дата основания не может быть в будущем");
            }
            city.foundationDate = foundationDate;
            return this;
        }

        public City build() {
            return city;
        }
    }

    public static ArrayListToSortByStrategy<City> loadDataFromFile(String pathToFile) {
        try {
            return Files.lines(Paths.get(pathToFile))
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 4)
                    // формат строки: name;latitude;longitude;foundationDate
                    .map(parts -> {
                        // формат строки: name;latitude;longitude;foundationDate
                        String name = parts[0];
                        double lat = Double.parseDouble(parts[1]);
                        double lon = Double.parseDouble(parts[2]);
                        String[] partsOfDate = parts[3].split("\\.");
                        int day = Integer.parseInt(partsOfDate[0]);
                        int month = Integer.parseInt(partsOfDate[1]);
                        int year = Integer.parseInt(partsOfDate[2]);
                        LocalDate date = LocalDate.of(year, month, day);
                        return new City.Builder()
                                .setName(name)
                                .setLatitude(lat)
                                .setLongitude(lon)
                                .setFoundationDate(date)
                                .build();
                    })
                    .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new ArrayListToSortByStrategy<>();
        }
    }

    public static ArrayListToSortByStrategy<City> loadRandomData(int elementsQty) {
        Faker faker = new Faker(new Locale("ru", "RU"));
        return IntStream.range(0, elementsQty)
                .mapToObj(i -> {
                    try {
                        String name = faker.address().cityName();
                        double lat = faker.number().randomDouble(6, -90, 90);
                        double lon = faker.number().randomDouble(6, -180, 180);
                        LocalDate date = LocalDate.of(
                                faker.number().numberBetween(1000, 2023),
                                faker.number().numberBetween(1, 12),
                                faker.number().numberBetween(1, 28)
                        );
                        return new City.Builder()
                                .setName(name)
                                .setLatitude(lat)
                                .setLongitude(lon)
                                .setFoundationDate(date)
                                .build();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
    }

    public static String readCityName(Scanner scanner, String prompt) {
        while(true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                if (input == null || input.trim().isEmpty()) {
                    throw new IllegalArgumentException("Название города не может быть пустым");
                }
                if (!input.matches("[\\p{L} .'-]+")) {
                    throw new IllegalArgumentException("Название города содержит недопустимые символы");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double readLatitude(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                double latitude = Double.parseDouble(input);
                if (latitude < -90.0 || latitude > 90.0) {
                    throw new IllegalArgumentException("Широта должна быть от -90 до 90 градусов");
                }
                return latitude;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: нужно ввести число. Попробуйте снова.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double readLongitude(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                double longitude = Double.parseDouble(input);
                if (longitude < -180.0 || longitude > 180.0) {
                    throw new IllegalArgumentException("Долготоа должна быть от -180 до 180 градусов");
                }
                return longitude;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: нужно ввести число. Попробуйте снова.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static LocalDate readDate(Scanner scanner, String prompt, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                LocalDate foundationDate = LocalDate.parse(input, formatter);
                if (foundationDate.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("Дата основания не может быть в будущем");
                }
                return foundationDate;
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: дата должна быть в формате " + pattern + ". Попробуйте снова.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static City createObjectManually(Scanner scanner) {
        String name = readCityName(scanner, "Введите название города:");

        double lat = readLatitude(scanner, "Введите широту (от -90 до 90 градусов):");
        double lon = readLongitude(scanner, "Введите долготу (от -180 до 180 градусов):");

        LocalDate date = readDate(scanner, "Введите дату основания (dd.MM.yyyy):", "dd.MM.yyyy");

        try {
            City city = new City.Builder()
                    .setName(name)
                    .setLatitude(lat)
                    .setLongitude(lon)
                    .setFoundationDate(date)
                    .build();
            return city;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayListToSortByStrategy<City> loadDataManually(Scanner scanner)  {
        System.out.println("Введите количество городов:");
        int qty = Integer.parseInt(scanner.nextLine());

        return IntStream.range(0, qty)
                .mapToObj(i -> {
                    System.out.println("Город №" + (i + 1));
                    try {
                        return createObjectManually(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
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

    public static void main(String[] args) {
        // Проверка валидности
        // Создание города с пустым названием
        try {
            System.out.println(new City.Builder()
                    .setName("")
                    .setLatitude(10.0)
                    .setLongitude(20.0)
                    .setFoundationDate(LocalDate.of(1900, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Создание города с недопустимым названием
        try {
            System.out.println(new City.Builder()
                    .setName("Мо%сква")
                    .setLatitude(10.0)
                    .setLongitude(20.0)
                    .setFoundationDate(LocalDate.of(1900, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Создание города с недопустимой широтой
        try {
            System.out.println(new City.Builder()
                    .setName("Москва")
                    .setLatitude(200.0)
                    .setLongitude(20.0)
                    .setFoundationDate(LocalDate.of(1900, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Создание города с недопустимой долготой
        try {
            System.out.println(new City.Builder()
                    .setName("Москва")
                    .setLatitude(20.0)
                    .setLongitude(500.0)
                    .setFoundationDate(LocalDate.of(1900, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Создание города с недопустимой датой
        try {
            System.out.println(new City.Builder()
                    .setName("Москва")
                    .setLatitude(20.0)
                    .setLongitude(50.0)
                    .setFoundationDate(LocalDate.of(2500, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Валидные данные
        try {
            System.out.println(new City.Builder()
                    .setName("Москва")
                    .setLatitude(20.0)
                    .setLongitude(50.0)
                    .setFoundationDate(LocalDate.of(800, 12, 30)).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

