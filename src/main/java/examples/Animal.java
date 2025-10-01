package examples;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import net.datafaker.Faker;

public class Animal {
    public enum Colours {BROWN, GREEN, BLUE, GRAY};

    final private String kind;
    final private boolean isWoolen;
    final private Colours eyesColour;

    private Animal(Builder builder){
        this.kind = builder.kind;
        this.eyesColour = builder.eyesColour;
        this.isWoolen = builder.isWoolen;
        validate();
    }

    private void validate(){
        if (kind == null){
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
        if (eyesColour == null){
            throw new IllegalArgumentException("Поле не может быть пустым");
        }
    }

    public String getKind() { return kind; }
    public boolean getIsWoolen() { return isWoolen; }
    public Colours getEyesColour() { return eyesColour; }

    public static class Builder {
        private String kind;
        private boolean isWoolen;
        private Colours eyesColour;

        public Builder kind(String kind){
            this.kind = kind;
            return this;
        }
        public Builder isWoolen (boolean isWoolen){
            this.isWoolen = isWoolen;
            return this;
        }
        public Builder eyesColour(Colours eyesColour){
            this.eyesColour = eyesColour;
            return this;
        }
        public Animal build() { return new Animal(this); }
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
        ArrayListToSortByStrategy<Animal> list = new ArrayListToSortByStrategy<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathToFile));
            for (String line : lines) {
                String[] parts = line.split(";");
                if (parts.length != 3) continue;
                String kind = parts[0].trim();
                Colours colour = Colours.valueOf(parts[1].trim().toUpperCase());
                boolean wool = Boolean.parseBoolean(parts[2].trim());
                list.add(new Animal.Builder().kind(kind).eyesColour(colour).isWoolen(wool).build());
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return list;
    }

    public static ArrayListToSortByStrategy<Animal> loadRandomData(int elementsQty) {
        ArrayListToSortByStrategy<Animal> list = new ArrayListToSortByStrategy<>();
        Faker faker = new Faker();
        Colours[] colours = Colours.values();
        for (int i = 0; i < elementsQty; i++) {
            String kind = faker.animal().name();
            Colours colour = colours[faker.random().nextInt(colours.length)];
            boolean wool = faker.bool().bool();
            list.add(new Animal.Builder().kind(kind).eyesColour(colour).isWoolen(wool).build());
        }
        return list;
    }

    public static ArrayListToSortByStrategy<Animal> loadDataManually() {
        ArrayListToSortByStrategy<Animal> list = new ArrayListToSortByStrategy<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Сколько животных добавить? ");
        int qty = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < qty; i++) {
            System.out.println("Животное №" + (i + 1));
            System.out.print("Вид: ");
            String kind = sc.nextLine();
            System.out.print("Цвет глаз (BROWN/GREEN/BLUE/GRAY): ");
            Colours colour = Colours.valueOf(sc.nextLine().trim().toUpperCase());
            System.out.print("Есть шерсть? (true/false): ");
            boolean wool = Boolean.parseBoolean(sc.nextLine());
            list.add(new Animal.Builder().kind(kind).eyesColour(colour).isWoolen(wool).build());
        }
        return list;
    }

    public static void main(String[] args) {

        System.out.println("Working dir: " + System.getProperty("user.dir"));

        try {
            Animal a1 = new Animal.Builder()
                    .kind("Dog").eyesColour(Colours.BLUE).isWoolen(true).build();
            System.out.println("Валидный: " + a1);
        } catch (Exception e) {
            System.out.println("Ошибка создания валидного: " + e.getMessage());
        }

        try {
            Animal a2 = new Animal.Builder()
                    .eyesColour(Colours.GREEN).isWoolen(false).build();
            System.out.println(a2);
        } catch (Exception e) {
            System.out.println("Ожидаемая ошибка (невалидный): " + e.getMessage());
        }

        ArrayListToSortByStrategy<Animal> fromFile =
                Animal.loadDataFromFile("animals.txt");
        System.out.println("Из файла:");
        fromFile.forEach(System.out::println);

        ArrayListToSortByStrategy<Animal> random =
                Animal.loadRandomData(5);
        System.out.println("Случайные данные:");
        random.forEach(System.out::println);

        //Ввод вручную
        //ArrayListToSortByStrategy<Animal> manual = Animal.loadDataManually();
        //manual.forEach(System.out::println);
    }
}


class ArrayListToSortByStrategy<T> extends ArrayList<T> {
}
