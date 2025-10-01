package ui;

import data.comparators.*;
import data.model.Animal;
import data.model.City;
import data.model.Person;
import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.SearchStrategy;
import domain.interfaces.SortStrategy;
import domain.search.BinarySearchStrategy;
import domain.sort.BubbleSortStrategy;
import domain.sort.QuickSortStrategy;

import java.util.Comparator;
import java.util.Scanner;

public class Program {
    private static ArrayListToSortByStrategy<?> collection = null;
    private static String currentType = null;
    private static SortStrategy sortStrategy = null;
    private static SearchStrategy searchStrategy = null;
    private static Comparator<?> comparator = null;
    private static boolean isSorted = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nГлавное меню:");
            System.out.println("1 - Ввод коллекции");
            System.out.println("2 - Взаимодействие с коллекцией");
            System.out.println("Q - Выход");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    inputMenu(scanner);
                    break;
                case "2":
                    interactionMenu(scanner);
                    break;
                case "Q":
                case "q":
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }

        System.out.println("Выход из программы...");
        scanner.close();
    }

    private static void inputMenu(Scanner scanner) {
        boolean inInputMenu = true;

        while (inInputMenu) {
            System.out.println("\nМеню ввода коллекции:");
            System.out.println("1 - Из файла");
            System.out.println("2 - Случайные значения");
            System.out.println("3 - Вручную");
            System.out.println("B - Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите путь к файлу: ");
                    String path = scanner.nextLine();

                    currentType = chooseType(scanner);
                    if (currentType == null) break;

                    switch (currentType) {
                        case "City":
                            collection = City.loadDataFromFile(path);
                            break;
                        case "Person":
                            collection = Person.loadDataFromFile(path);
                            break;
                        case "Animal":
                            collection = Animal.loadDataFromFile(path);
                            break;
                    }
                    collection.forEach(System.out::println);
                    isSorted = false;
                    break;

                case "2":
                    currentType = chooseType(scanner);
                    if (currentType == null) break;

                    System.out.print("Введите количество элементов: ");
                    int qty = Integer.parseInt(scanner.nextLine());

                    switch (currentType) {
                        case "City":
                            collection = City.loadRandomData(qty);
                            break;
                        case "Person":
                            collection = Person.loadRandomData(qty);
                            break;
                        case "Animal":
                            collection = Animal.loadRandomData(qty);
                            break;
                    }
                    collection.forEach(System.out::println);
                    isSorted = false;
                    break;

                case "3":
                    currentType = chooseType(scanner);
                    if (currentType == null) break;

                    switch (currentType) {
                        case "City":
                            collection = City.loadDataManually(scanner);
                            break;
                        case "Person":
                            collection = Person.loadDataManually();
                            break;
                        case "Animal":
                            collection = Animal.loadDataManually();
                            break;
                    }
                    collection.forEach(System.out::println);
                    isSorted = false;
                    break;

                case "B":
                case "b":
                    inInputMenu = false;
                    break;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void interactionMenu(Scanner scanner) {
        if (collection == null) {
            System.out.println("Коллекция ещё не создана!");
            return;
        }

        boolean inInteractionMenu = true;

        while (inInteractionMenu) {
            System.out.println("\nМеню взаимодействия:");
            System.out.println("1 - Вывод коллекции");
            System.out.println("2 - Сортировка коллекции");
            System.out.println("3 - Поиск элемента");
            System.out.println("B - Назад");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    collection.forEach(System.out::println);
                    break;

                case "2":
                    sortStrategy = chooseSortStrategy(scanner);
                    if (sortStrategy == null) break;

                    System.out.println("Сортировка:");
                    if ("City".equals(currentType)) {
                        System.out.println("1 - С севера на юг");
                        System.out.println("2 - С запада на восток");
                        System.out.println("3 - По названию");
                        System.out.println("4 - По дате основания");
                        System.out.println("5 - По удаленности от города");
                        String sortCriterion = scanner.nextLine();
                        if ("1".equals(sortCriterion)) {
                            comparator = new CityLatitudeComparator();
                        } else if ("2".equals(sortCriterion)) {
                            comparator = new CityLongitudeComparator();
                        } else if ("3".equals(sortCriterion)) {
                            comparator = new CityNameComparator();
                        } else if ("4".equals(sortCriterion)) {
                            comparator = new CityFoundationDateComparator();
                        } else if ("5".equals(sortCriterion)) {
                            System.out.print("Введите название города: ");
                            String comparedCityName = scanner.nextLine();
                            City comparedCity = ((ArrayListToSortByStrategy<City>) collection).stream()
                                    .filter(c -> c.getCityName().equals(comparedCityName))
                                    .findFirst()
                                    .orElse(null);
                            if (comparedCity != null) {
                                comparator = new CityDistanceComparator(comparedCity.getLatitude(), comparedCity.getLongitude());
                            } else comparator = null;
                        }
                        if (comparator != null) {
                            collection = ((ArrayListToSortByStrategy<City>) collection).sortByStrategy(sortStrategy, (Comparator<City>) comparator);
                            isSorted = true;
                            collection.forEach(System.out::println);
                        }
                    } else if ("Person".equals(currentType)) {
                        System.out.println("1 - По полу");
                        System.out.println("2 - По возрасту");
                        System.out.println("3 - По фамилии");
                        String sortCriterion = scanner.nextLine();
                        if ("1".equals(sortCriterion)) {
                            comparator = new PersonSexComparator();
                        } else if ("2".equals(sortCriterion)) {
                            comparator = new PersonAgeComparator();
                        } else if ("3".equals(sortCriterion)) {
                            comparator = new PersonSurnameComparator();
                        }
                        if (comparator != null) {
                            collection = ((ArrayListToSortByStrategy<Person>) collection).sortByStrategy(sortStrategy, (Comparator<Person>) comparator);
                            isSorted = true;
                            collection.forEach(System.out::println);
                        }
                    } else if ("Animal".equals(currentType)) {
                        System.out.println("1 - По виду");
                        System.out.println("2 - По цвету глаз");
                        System.out.println("3 - По наличию шерсти");
                        String sortCriterion = scanner.nextLine();
                        if ("1".equals(sortCriterion)) {
                            comparator = new AnimalKindComparator();
                        } else if ("2".equals(sortCriterion)) {
                            comparator = new AnimalEyesColorComparator();
                        } else if ("3".equals(sortCriterion)) {
                            comparator = new AnimalIsWoolenComparator();
                        }
                        if (comparator != null) {
                            collection = ((ArrayListToSortByStrategy<Animal>) collection).sortByStrategy(sortStrategy, (Comparator<Animal>) comparator);
                            isSorted = true;
                            collection.forEach(System.out::println);
                        }
                    } else {
                        System.out.println("Сортировка для " + currentType + " пока не реализована.");
                    }

                    break;

                case "3":
                    searchStrategy = chooseSearchStrategy(scanner);
                    if (searchStrategy == null) break;

                    if ("1".equals(searchStrategy) && !isSorted) {
                        System.out.println("Сначала отсортируйте коллекцию!");
                    } else {
                        System.out.println("Введите параметры искомого объекта...");
                        Object keyObject = createKeyObject(currentType, scanner);
                        switch (currentType) {
                            case "City":
                                City cityKey = (City) keyObject;
                                ((ArrayListToSortByStrategy<City>) collection)
                                        .searchByStrategy(searchStrategy, cityKey, (Comparator<City>) comparator);
                                break;
                            case "Person":
                                Person personKey = (Person) keyObject;
                                ((ArrayListToSortByStrategy<Person>) collection)
                                        .searchByStrategy(searchStrategy, personKey, (Comparator<Person>) comparator);
                                break;
                            case "Animal":
                                Animal animalKey = (Animal) keyObject;
                                ((ArrayListToSortByStrategy<Animal>) collection)
                                        .searchByStrategy(searchStrategy, animalKey, (Comparator<Animal>) comparator);
                                break;
                        }
                    }
                    break;

                case "B":
                case "b":
                    inInteractionMenu = false;
                    break;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static String chooseType(Scanner scanner) {
        System.out.println("\nВыберите тип коллекции:");
        System.out.println("1 - Города");
        System.out.println("2 - Люди");
        System.out.println("3 - Животные");
        String type = scanner.nextLine();
        switch (type) {
            case "1": return "City";
            case "2": return "Person";
            case "3": return "Animal";
            default:
                System.out.println("Неверный выбор.");
                return null;
        }
    }

    private static SortStrategy chooseSortStrategy(Scanner scanner) {
        System.out.println("\nВыберите алгоритм сортировки:");
        System.out.println("1 - Сортировка пузырьком (Bubble Sort)");
        System.out.println("2 - Быстрая сортировка (Quick Sort)");
        String type = scanner.nextLine();
        switch (type) {
            case "1":
                return new BubbleSortStrategy();
            case "2":
                return new QuickSortStrategy();
            default:
                System.out.println("Неверный выбор.");
                return null;
        }
    }

    private static SearchStrategy chooseSearchStrategy(Scanner scanner) {
        System.out.println("\nВыберите алгоритм поиска:");
        System.out.println("1 - Бинарный поиск (Binary Search)");
        String type = scanner.nextLine();
        switch (type) {
            case "1": return new BinarySearchStrategy();
            default:
                System.out.println("Неверный выбор.");
                return null;
        }
    }

    private static Object createKeyObject(String type, Scanner scanner) {
        switch (type) {
            case "City":
                City city = City.createObjectManually(scanner);
                return city;
            case "Person":
                Person person = Person.createObjectManually(scanner);
                return person;
            case "Animal":
                Animal animal = Animal.createObjectManually(scanner);
                return animal;
            default:
                System.out.println("Неверный выбор.");
                return null;
        }
    }
}
