package data.model;

import data.repository.ArrayListToSortByStrategy;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.datafaker.Faker;

public class Animal {
    public enum Colours {BROWN, GREEN, BLUE, GRAY}

    ;

    final private String kind;
    final private boolean isWoolen;
    final private Colours eyesColour;

    private Animal(Builder builder) {
        this.kind = builder.kind;
        this.eyesColour = builder.eyesColour;
        this.isWoolen = builder.isWoolen;
        validate();
    }

    private void validate() {
        if (kind == null) {
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
        if (eyesColour == null) {
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
    }

    public String getKind() {
        return kind;
    }

    public boolean getIsWoolen() {
        return isWoolen;
    }

    public Colours getEyesColor() {
        return eyesColour;
    }

    public static class Builder {
        private String kind;
        private boolean isWoolen;
        private Colours eyesColour;

        public Builder kind(String kind) {
            this.kind = kind;
            return this;
        }

        public Builder isWoolen(boolean isWoolen) {
            this.isWoolen = isWoolen;
            return this;
        }

        public Builder eyesColour(Colours eyesColour) {
            this.eyesColour = eyesColour;
            return this;
        }

        public Animal build() {
            return new Animal(this);
        }
    }

    public String toString() {
        return String.format("Вид животного: %s; цвет глаз: %s; наличие шерсти: %s", kind, eyesColour, isWoolen);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return isWoolen == animal.isWoolen && Objects.equals(kind, animal.kind) && eyesColour == animal.eyesColour;
    }

    public int hashCode() {
        return Objects.hash(kind, eyesColour, isWoolen);
    }

    public static ArrayListToSortByStrategy<Animal> loadDataFromFile(String pathToFile) {
        try {
            return Files.lines(Paths.get(pathToFile))
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> new Animal.Builder()
                            .kind(parts[0].trim())
                            .eyesColour(Colours.valueOf(parts[1].trim().toUpperCase()))
                            .isWoolen(Boolean.parseBoolean(parts[2].trim()))
                            .build())
                    .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return new ArrayListToSortByStrategy<>();
        }
    }

    public static ArrayListToSortByStrategy<Animal> loadRandomData(int elementsQty) {
        Faker faker = new Faker();
        Colours[] colours = Colours.values();

        return IntStream.range(0, elementsQty)
                .mapToObj(i -> new Animal.Builder()
                        .kind(faker.animal().name())
                        .eyesColour(colours[faker.random().nextInt(colours.length)])
                        .isWoolen(faker.bool().bool())
                        .build())
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
    }

    public static ArrayListToSortByStrategy<Animal> loadDataManually() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Сколько животных добавить? ");
        int qty = Integer.parseInt(sc.nextLine());

        return IntStream.range(0, qty)
                .mapToObj(i -> {
                    System.out.println("Животное №" + (i + 1));
                    System.out.print("Вид: ");
                    String kind = sc.nextLine();
                    System.out.print("Цвет глаз (BROWN/GREEN/BLUE/GRAY): ");
                    Colours colour = Colours.valueOf(sc.nextLine().trim().toUpperCase());
                    System.out.print("Есть шерсть? (true/false): ");
                    boolean wool = Boolean.parseBoolean(sc.nextLine());
                    return new Animal.Builder().kind(kind).eyesColour(colour).isWoolen(wool).build();
                })
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
    }

    public static Animal createObjectManually(Scanner sc) {
        System.out.println("Введите данные животного:");
        System.out.print("Вид: ");
        String kind = sc.nextLine();
        System.out.print("Цвет глаз (BROWN/GREEN/BLUE/GRAY): ");
        Colours colour = Colours.valueOf(sc.nextLine().trim().toUpperCase());
        System.out.print("Есть шерсть? (true/false): ");
        boolean wool = Boolean.parseBoolean(sc.nextLine());

        return new Animal.Builder()
                .kind(kind)
                .eyesColour(colour)
                .isWoolen(wool)
                .build();
    }
}