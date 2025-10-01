package examples;

import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

enum Sex {
    FEMALE, MALE;

    /**
     * Парсит строку в enum, игнорируя регистр и пробелы
     *
     * @param input входная строка
     * @return соответствующий enum или null, если не найден
     */
    public static Sex parseSexIgnoreCase(String input) {
        if (input == null) return null;

        for (Sex sex : Sex.values()) {
            if (sex.name().equalsIgnoreCase(input.trim())) {
                return sex;
            }
        }
        return null;
    }

    public static boolean isValid(String input) {
        return parseSexIgnoreCase(input) != null;
    }
}

class PersonDataException extends RuntimeException {
    public PersonDataException(String message) {
        super(message);
    }
}

public class Person {
    private Sex sex;
    private int age;
    private String surname;

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getSurname() {
        return surname;
    }

    private Person() {
    }

    public Person(String sex, String surname, int age) {
        if (!Sex.isValid(sex)) {
            throw new PersonDataException("Пол введен неверно");
        }
        if (!Character.isUpperCase(surname.charAt(0))) {
            throw new PersonDataException("Фамилия должна начинаться с заглавной буквы");
        }
        if (age < 0) {
            throw new PersonDataException("Возраст не может быть отрицательный");
        }
        this.sex = Sex.parseSexIgnoreCase(sex);
        this.surname = surname;
        this.age = age;
    }

    public static class Builder {
        Person person;

        Builder() {
            person = new Person();
        }

        public Builder setPersonSex(Sex sex) {
            person.sex = sex;
            return this;
        }

        public Builder setPersonAge(int age) {
            person.age = age;
            return this;
        }

        public Builder setPersonSurname(String surname) {
            person.surname = surname;
            return this;
        }

        public Person build() {
            return person;
        }
    }

    public static Person createObjectManually(Scanner scanner) {
        System.out.println("\n=== Создание объекта Person ===");

        // Ввод фамилии
        System.out.print("Введите фамилию (с заглавной буквы): ");
        String surname = scanner.nextLine();

        // Ввод пола
        Sex sex = null;
        while (sex == null) {
            System.out.print("Введите пол (MALE/FEMALE): ");
            String sexInput = scanner.nextLine().trim().toUpperCase();
            try {
                sex = Sex.valueOf(sexInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка! Допустимые значения: MALE или FEMALE");
            }
        }

        // Ввод возраста
        int age = 0;
        while (age <= 0) {
            System.out.print("Введите возраст (положительное число): ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("Ошибка! Возраст должен быть положительным числом.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите корректное число.");
            }
        }

        // Создание и возврат объекта
        return new Builder().setPersonSurname(surname).setPersonSex(sex).setPersonAge(age).build();
    }

    /**
     * Заполняет коллекцию вручную через ввод данных в терминале
     */
    public static ArrayListToSortByStrategy<Person> loadDataManually() {
        Scanner scanner = new Scanner(System.in);
        ArrayListToSortByStrategy<Person> persons = new ArrayListToSortByStrategy<>();

        System.out.println("\n=== Заполнение коллекции Person ===");

        boolean continueAdding = true;
        while (continueAdding) {
            // Создаем один объект
            Person person = createObjectManually(scanner);
            persons.add(person);

            System.out.println("Объект добавлен: " + person);

            // Спрашиваем, продолжать ли ввод
            System.out.print("\nДобавить еще одного человека? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (!answer.equals("y") && !answer.equals("yes") && !answer.equals("д") && !answer.equals("да")) {
                continueAdding = false;
            }
        }

        System.out.println("Коллекция заполнена. Всего объектов: " + persons.size());
        return persons;
    }

    @Override
    public String toString() {
        return ("Фамилия: " + this.surname + "; Пол: " + this.sex.toString() + "; Возраст: " + this.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex, surname, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        return age == person.age && sex == person.sex && Objects.equals(surname, person.surname);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ТЕСТИРОВАНИЕ КЛАССА PERSON ===\n");

        // Тест 1: Создание экземпляров через конструктор - Удачное
        System.out.println("1. ТЕСТ КОНСТРУКТОРА С ВАЛИДНЫМИ ЗНАЧЕНИЯМИ:");
        try {
            Person person1 = new Person("MALE", "Ivanov", 25);
            System.out.println("✓ Успешно создан: " + person1);

            Person person2 = new Person("FEMALE", "Petrova", 30);
            System.out.println("✓ Успешно создан: " + person2);

            Person person3 = new Person("male", "Sidorov", 22); // проверка регистра
            System.out.println("✓ Успешно создан: " + person3);
        } catch (PersonDataException e) {
            System.out.println("✗ Ошибка: " + e.getMessage());
        }

        // Тест 2: Создание экземпляров через конструктор - Неудачное
        System.out.println("\n2. ТЕСТ КОНСТРУКТОРА С НЕВАЛИДНЫМИ ЗНАЧЕНИЯМИ:");

        // Неверный пол
        try {
            Person invalid1 = new Person("UNKNOWN", "Ivanov", 25);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + invalid1);
        } catch (PersonDataException e) {
            System.out.println("✓ Правильно отловил ошибку пола: " + e.getMessage());
        }

        // Фамилия с маленькой буквы
        try {
            Person invalid2 = new Person("MALE", "ivanov", 25);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + invalid2);
        } catch (PersonDataException e) {
            System.out.println("✓ Правильно отловил ошибку фамилии: " + e.getMessage());
        }

        // Отрицательный возраст
        try {
            Person invalid3 = new Person("FEMALE", "Petrova", -5);
            System.out.println("✗ Ожидалась ошибка, но объект создан: " + invalid3);
        } catch (PersonDataException e) {
            System.out.println("✓ Правильно отловил ошибку возраста: " + e.getMessage());
        }

        // Тест 3: через Builder
        System.out.println("\n3. ТЕСТ СОЗДАНИЯ ЧЕРЕЗ BUILDER:");
        Person builderPerson = new Person.Builder().setPersonSurname("Kuznetsov").setPersonSex(Sex.MALE).setPersonAge(35).build();
        System.out.println("✓ Создан через Builder: " + builderPerson);

        // Тест 4: toString()
        System.out.println("\n4. ТЕСТ МЕТОДА toString():");
        Person testPerson = new Person("FEMALE", "Smirnova", 28);
        System.out.println("toString() результат: " + testPerson.toString());

        // Тест 5: equals() и hashCode()
        System.out.println("\n5. ТЕСТ equals() И hashCode():");
        Person personA = new Person("MALE", "Ivanov", 25);
        Person personB = new Person("MALE", "Ivanov", 25);
        Person personC = new Person("FEMALE", "Ivanova", 25);

        System.out.println("personA.equals(personB): " + personA.equals(personB));
        System.out.println("personA.equals(personC): " + personA.equals(personC));
        System.out.println("personA.hashCode() == personB.hashCode(): " + (personA.hashCode() == personB.hashCode()));

        // Тест 6: ArrayListToSortByStrategy
        System.out.println("\n6. ТЕСТ РАБОТЫ С КОЛЛЕКЦИЕЙ:");
        System.out.print("Хотите протестировать ручной ввод данных? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("y") || answer.equals("yes") || answer.equals("д") || answer.equals("да")) {
            ArrayListToSortByStrategy<Person> persons = loadDataManually();

            System.out.println("\n=== ДЕМОНСТРАЦИЯ РАБОТЫ С КОЛЛЕКЦИЕЙ ===");
            System.out.println("Размер коллекции: " + persons.size());
            System.out.println("Содержимое коллекции:");
            for (int i = 0; i < persons.size(); i++) {
                System.out.println("  " + i + ": " + persons.get(i));
            }

            // сортировка по фамилии
            System.out.println("\n--- СОРТИРОВКА ПО ФАМИЛИИ ---");
            Comparator<Person> bySurname = Comparator.comparing(Person::getSurname);
            persons.sortByStrategy(bySurname);
            System.out.println("После сортировки по фамилии:");
            for (int i = 0; i < persons.size(); i++) {
                System.out.println("  " + i + ": " + persons.get(i));
            }

            // бинарный поиска
            if (!persons.isEmpty()) {
                System.out.println("\n--- БИНАРНЫЙ ПОИСК ---");
                Person searchPerson = persons.get(0); // берем первый элемент для поиска
                int foundIndex = persons.binarySearch(searchPerson, bySurname);
                System.out.println("Поиск " + searchPerson.getSurname() + ": найден по индексу " + foundIndex);
            }

            // мин/макс
            System.out.println("\n--- ПОИСК МИНИМУМА И МАКСИМУМА ---");
            Comparator<Person> byAge = Comparator.comparingInt(Person::getAge);
            Person youngest = persons.min(byAge);
            Person oldest = persons.max(byAge);
            if (youngest != null) {
                System.out.println("Самый молодой: " + youngest);
            }
            if (oldest != null) {
                System.out.println("Самый старший: " + oldest);
            }
        }

        System.out.println("\n=== ТЕСТИРОВАНИЕ ЗАВЕРШЕНО ===");
        scanner.close();
    }
}
