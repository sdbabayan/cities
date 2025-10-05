package data.model;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.IntValueReturnable;
import domain.search.BinarySearchStrategy;
import domain.sort.BubbleSortStrategy;
import domain.sort.EvenSortStrategy;
import domain.sort.MergeSortStrategy;
import domain.sort.QuickSortStrategy;
import net.datafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Person implements IntValueReturnable {
    private Sex sex;
    private int age;
    private String surname;

    private Person() {
    }

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getSurname() {
        return surname;
    }

    public static class Builder {
        Person person = new Person();

        Builder() {
        }

        public Builder setPersonSex(Sex sex) {
            this.person.sex = sex;
            return this;
        }

        public Builder setPersonAge(int age) {
            this.person.age = age;
            return this;
        }

        public Builder setPersonSurname(String surname) {
            this.person.surname = surname;
            return this;
        }

        public Person build() {
            return this.person;
        }
    }

    public enum Sex {
        FEMALE,
        MALE;

        private Sex() {
        }

        public static Sex parseSexIgnoreCase(String input) {
            if (input == null) {
                return null;
            } else {
                Sex[] var1 = values();
                int var2 = var1.length;

                for (int var3 = 0; var3 < var2; ++var3) {
                    Sex sex = var1[var3];
                    if (sex.name().equalsIgnoreCase(input.trim())) {
                        return sex;
                    }
                }

                return null;
            }
        }

        public static boolean isValid(String input) {
            return parseSexIgnoreCase(input) != null;
        }
    }


    public static ArrayListToSortByStrategy<Person> loadDataFromFile(String pathToFile) {
        try {
            return Files.lines(Paths.get(pathToFile))
                    .map(line -> line.split(";"))
                    .filter(parts -> parts.length == 3)
                    .map(parts -> {
                        try {
                            String surname = parts[0].trim();
                            String sex = parts[1].trim();
                            int age = Integer.parseInt(parts[2].trim());
                            return new Person(sex, surname, age);
                        } catch (PersonDataException e) {
                            System.out.println("Ошибка данных: " + e.getMessage() + " в строке: " + String.join(";", parts));
                            return null;
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный формат возраста в строке: " + String.join(";", parts));
                            return null;
                        } catch (Exception e) {
                            System.out.println("Неожиданная ошибка в строке: " + String.join(";", parts) + " - " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return new ArrayListToSortByStrategy<>();
        }
    }

    public static ArrayListToSortByStrategy<Person> loadRandomData(int elementsQty) {
        Faker faker = new Faker(new Locale("ru", "RU"));

        String[] russianSurnames = {
                "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
                "Попов", "Васильев", "Павлов", "Семенов", "Голубев",
                "Виноградов", "Богданов", "Воробьев", "Федоров", "Михайлов"
        };

        return IntStream.range(0, elementsQty)
                .mapToObj(i -> {
                    try {
                        String surname = russianSurnames[faker.random().nextInt(russianSurnames.length)];
                        Sex sex = faker.random().nextBoolean() ? Sex.MALE : Sex.FEMALE;
                        int age = faker.number().numberBetween(18, 80);

                        return new Person.Builder()
                                .setPersonSurname(surname)
                                .setPersonSex(sex)
                                .setPersonAge(age)
                                .build();
                    } catch (Exception e) {
                        System.out.println("Ошибка создания случайного Person: " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
    }

    public static Person createObjectManually(Scanner scanner) {
        System.out.println("\n=== Создание объекта Person ===");
        System.out.print("Введите фамилию (с заглавной буквы): ");
        String surname = scanner.nextLine();
        Sex sex = null;

        while (sex == null) {
            System.out.print("Введите пол (MALE/FEMALE): ");
            String sexInput = scanner.nextLine().trim().toUpperCase();

            try {
                sex = Sex.valueOf(sexInput);
            } catch (IllegalArgumentException var6) {
                System.out.println("Ошибка! Допустимые значения: MALE или FEMALE");
            }
        }

        int age = 0;

        while (age <= 0) {
            System.out.print("Введите возраст (положительное число): ");

            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("Ошибка! Возраст должен быть положительным числом.");
                }
            } catch (NumberFormatException var5) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }

        return (new Builder()).setPersonSurname(surname).setPersonSex(sex).setPersonAge(age).build();
    }

    public static ArrayListToSortByStrategy<Person> loadDataManually() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Заполнение коллекции Person ===");

        System.out.print("Сколько человек добавить? ");
        int qty = Integer.parseInt(scanner.nextLine());

        return IntStream.range(0, qty)
                .mapToObj(i -> {
                    try {
                        System.out.println("\nЧеловек №" + (i + 1));
                        Person person = createObjectManually(scanner);
                        System.out.println("Объект добавлен: " + person);
                        return person;
                    } catch (Exception e) {
                        System.out.println("Ошибка при создании человека: " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayListToSortByStrategy::new));
    }

    public Person(String sex, String surname, int age) {
        if (!Sex.isValid(sex)) {
            throw new PersonDataException("Пол введен неверно");
        } else if (!Character.isUpperCase(surname.charAt(0))) {
            throw new PersonDataException("Фамилия должна начинаться с заглавной буквы");
        } else if (age < 0) {
            throw new PersonDataException("Возраст не может быть отрицательный");
        } else {
            this.sex = Sex.parseSexIgnoreCase(sex);
            this.surname = surname;
            this.age = age;
        }
    }

    @Override
    public int getIntValue() {
        return age;
    }

    public String toString() {
        String var10000 = this.surname;
        return "Фамилия: " + var10000 + "; Пол: " + this.sex.toString() + "; Возраст: " + this.age;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.sex, this.surname, this.age});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Person person = (Person) o;
            return this.age == person.age && this.sex == person.sex && Objects.equals(this.surname, person.surname);
        } else {
            return false;
        }
    }


    /**
     * Дополнительное задание №2 частный случай для класса Person
     * Записывает всю коллекцию в файл (добавляет в конец)
     */
    public static void writeCollectionToFile(ArrayListToSortByStrategy<Person> persons, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("=== Коллекция Person (" + persons.size() + " записей) ===\n");
            for (Person person : persons) {
                writer.write(person.toString());
                writer.newLine();
            }
            writer.write("=== Конец коллекции ===\n\n");
            System.out.println("Коллекция записана в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Записывает один найденный объект в файл (добавляет в конец)
     */
    public static void writeFoundObjectToFile(Person person, String filename, String searchInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("НАЙДЕННЫЙ ОБЪЕКТ: " + searchInfo + "\n");
            writer.write(person.toString());
            writer.newLine();
            writer.write("---\n");
            System.out.println("Найденный объект записан в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Записывает результаты поиска в файл
     */
    public static void writeSearchResultToFile(boolean found, Person searchObject, String filename, String strategy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("РЕЗУЛЬТАТ ПОИСКА (" + strategy + "):\n");
            writer.write("Искомый объект: " + searchObject.toString() + "\n");
            writer.write("Результат: " + (found ? "НАЙДЕН" : "НЕ НАЙДЕН") + "\n");
            writer.write("---\n");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

         System.out.println("=== ТЕСТИРОВАНИЕ КЛАССА PERSON ===\n");

        System.out.println("1. ТЕСТ КОНСТРУКТОРА С ВАЛИДНЫМИ ЗНАЧЕНИЯМИ:");

        Person builderPerson;
        Person testPerson;
        Person personA;
        try {
            builderPerson = new Person("MALE", "Ivanov", 25);
            System.out.println("✓ Успешно создан: " + String.valueOf(builderPerson));
            testPerson = new Person("FEMALE", "Petrova", 30);
            System.out.println("✓ Успешно создан: " + String.valueOf(testPerson));
            personA = new Person("male", "Sidorov", 22);
            System.out.println("✓ Успешно создан: " + String.valueOf(personA));
        } catch (PersonDataException var16) {
            System.out.println("✗ Ошибка: " + var16.getMessage());
        }

        System.out.println("\n2. ТЕСТ КОНСТРУКТОРА С НЕВАЛИДНЫМИ ЗНАЧЕНИЯМИ:");

        try {
            builderPerson = new Person("UNKNOWN", "Ivanov", 25);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + String.valueOf(builderPerson));
        } catch (PersonDataException var15) {
            System.out.println("✓ Правильно отловил ошибку пола: " + var15.getMessage());
        }

        try {
            builderPerson = new Person("MALE", "ivanov", 25);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + String.valueOf(builderPerson));
        } catch (PersonDataException var14) {
            System.out.println("✓ Правильно отловил ошибку фамилии: " + var14.getMessage());
        }

        try {
            builderPerson = new Person("FEMALE", "Petrova", -5);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + String.valueOf(builderPerson));
        } catch (PersonDataException var13) {
            System.out.println("✓ Правильно отловил ошибку возраста: " + var13.getMessage());
        }

        System.out.println("\n3. ТЕСТ СОЗДАНИЯ ЧЕРЕЗ BUILDER:");
        builderPerson = (new Builder()).setPersonSurname("Kuznetsov").setPersonSex(Sex.MALE).setPersonAge(35).build();
        PrintStream var10000 = System.out;
        String var10001 = String.valueOf(builderPerson);
        var10000.println("✓ Создан через Builder: " + var10001);

        System.out.println("\n4. ТЕСТ МЕТОДА toString():");
        testPerson = new Person("FEMALE", "Smirnova", 28);
        var10000 = System.out;
        var10001 = testPerson.toString();
        var10000.println("toString() результат: " + var10001);

        System.out.println("\n5. ТЕСТ equals() И hashCode():");
        personA = new Person("MALE", "Ivanov", 25);
        Person personB = new Person("MALE", "Ivanov", 25);
        Person personC = new Person("FEMALE", "Ivanova", 25);
        var10000 = System.out;
        boolean var21 = personA.equals(personB);
        var10000.println("personA.equals(personB): " + var21);
        var10000 = System.out;
        var21 = personA.equals(personC);
        var10000.println("personA.equals(personC): " + var21);
        System.out.println("personA.hashCode() == personB.hashCode(): " + (personA.hashCode() == personB.hashCode()));

        // Тестирование загрузки из файла
        System.out.println("\n6. ЗАГРУЗКА ИЗ ФАЙЛА:");
        ArrayListToSortByStrategy<Person> personsFromFile = Person.loadDataFromFile("persons.txt");
        System.out.println("Загружено из файла: " + personsFromFile.size() + " записей");

        // Тестирование случайных данных
        System.out.println("\n--- СЛУЧАЙНЫЕ ДАННЫЕ ---");
        ArrayListToSortByStrategy<Person> randomPersons = Person.loadRandomData(10);
        System.out.println("Сгенерировано случайных: " + randomPersons.size() + " записей");

        // Вывод результатов
        for (int i = 0; i < randomPersons.size(); i++) {
            System.out.println("  " + i + ": " + randomPersons.get(i));
        }

        System.out.println("\n7. ТЕСТ РАБОТЫ С КОЛЛЕКЦИЕЙ:");
        System.out.print("Хотите протестировать ручной ввод данных? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("y") || answer.equals("yes") || answer.equals("д") || answer.equals("да")) {
            ArrayListToSortByStrategy<Person> persons = loadDataManually();
            System.out.println("\n=== ДЕМОНСТРАЦИЯ РАБОТЫ С КОЛЛЕКЦИЕЙ ===");
            System.out.println("Размер коллекции: " + persons.size());
            System.out.println("Содержимое коллекции:");

            for (int i = 0; i < persons.size(); ++i) {
                System.out.println("  " + i + ": " + String.valueOf(persons.get(i)));
            }

            System.out.println("\n--- СОРТИРОВКА ПО ФАМИЛИИ ---");
            Comparator<Person> bySurname = Comparator.comparing(Person::getSurname);
            persons.sortByStrategy(new BubbleSortStrategy(), bySurname);
            System.out.println("После сортировки по фамилии:");

            for (int i = 0; i < persons.size(); ++i) {
                System.out.println("  " + i + ": " + String.valueOf(persons.get(i)));
            }

            if (!persons.isEmpty()) {
                System.out.println("\n--- БИНАРНЫЙ ПОИСК ---");
                Person searchPerson = (Person) persons.get(0);
                Person foundPerson = persons.searchByStrategy(new BinarySearchStrategy<>(), searchPerson, bySurname);

                System.out.println("Поиск " + searchPerson.getSurname() + ": " + (foundPerson != null ? "НАЙДЕН " + foundPerson : "НЕ НАЙДЕН"));

                // Дополнительно: поиск несуществующего элемента
                Person nonExistentPerson = new Person("MALE", "Несуществующий", 99);
                Person notFoundPerson = persons.searchByStrategy(new BinarySearchStrategy<>(), nonExistentPerson, bySurname);
                System.out.println("Поиск несуществующего: " + (notFoundPerson != null ? "НАЙДЕН " + notFoundPerson : "НЕ НАЙДЕН - корректно"));
            }
        }

        // Тестирование поиска в заранее загруженных данных
        System.out.println("\n8. ТЕСТ ПОИСКА В СЛУЧАЙНЫХ ДАННЫХ:");
        ArrayListToSortByStrategy<Person> testPersons = Person.loadRandomData(5);
        if (!testPersons.isEmpty()) {
            Comparator<Person> byAge = Comparator.comparingInt(Person::getAge);

            // Сортируем для бинарного поиска
            testPersons.sortByStrategy(new BubbleSortStrategy<>(), byAge);

            // Ищем человека с возрастом как у первого в списке
            Person searchTarget = testPersons.get(0);
            Person ageFoundPerson = testPersons.searchByStrategy(new BinarySearchStrategy<>(), searchTarget, byAge);

            System.out.println("Поиск по возрасту " + searchTarget.getAge() + ": " +
                    (ageFoundPerson != null ? "НАЙДЕН " + ageFoundPerson : "НЕ НАЙДЕН"));

            // Вывод тестовой коллекции
            System.out.println("Тестовая коллекция:");
            for (int i = 0; i < testPersons.size(); i++) {
                System.out.println("  " + i + ": " + testPersons.get(i));
            }
        }
        System.out.println("\n=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
        scanner.close();
    }

}
