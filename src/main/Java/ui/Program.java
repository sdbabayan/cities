package ui;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static List<?> collection = null;
    private static String currentType = null; // выбранный класс
    private static Class<?> clazz = null; // выбранный класс
    private static SortStrategy sortStrategy = null;
    private static SearchStrategy searchStrategy = null;
    private static Comparator<?> comparator = null;
    private static boolean isSorted = false;  // для бинарного поиска

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
                            System.out.println("вызван метод City.loadDataFromFile(" + path + ")");
                            //collection = City.loadDataFromFile(path);
                            break;
                        case "Person":
                            System.out.println("вызван метод Person.loadDataFromFile(" + path + ")");
                            //collection = Person.loadDataFromFile(path);
                            break;
                        case "Animal":
                            System.out.println("вызван метод Animal.loadDataFromFile(" + path + ")");
                            //collection = Animal.loadDataFromFile(path);
                            break;
                    }
                    isSorted = false;
                    break;

                case "2":
                    System.out.print("Введите количество элементов: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    currentType = chooseType(scanner);
                    if (currentType == null) break;

                    switch (currentType) {
                        case "City":
                            System.out.println("вызван метод City.loadRandomData(" + qty + ")");
                            //collection = City.loadRandomData(qty);
                            break;
                        case "Person": System.out.println("вызван метод Person.loadRandomData(" + qty + ")");
                            //collection = Person.loadRandomData(qty);
                            break;
                        case "Animal": System.out.println("вызван метод Animal.loadRandomData(" + qty + ")");
                            //collection = Animal.loadRandomData(qty);
                            break;
                    }
                    isSorted = false;
                    break;

                case "3":
                    currentType = chooseType(scanner);
                    if (currentType == null) break;

                    switch (currentType) {
                        case "City": System.out.println("вызван метод City.loadDataManually()");
                            //collection = City.loadDataManually();
                            break;
                        case "Person": System.out.println("вызван метод Person.loadDataManually()");
                            //collection = Person.loadDataManually();
                            break;
                        case "Animal": System.out.println("вызван метод Animal.loadDataManually()");
                            //collection = Animal.loadDataManually();
                            break;
                    }
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
                        String sortCriterion = scanner.nextLine();
                        if ("1".equals(sortCriterion)) {
                            comparator = new CityLatitudeComparator();
                        } else if ("2".equals(sortCriterion)) {
                            comparator = new CitylongitudeComparator();
                        } else if ("3".equals(sortCriterion)) {
                            comparator = new CityNameComparator();
                        } else if ("4".equals(sortCriterion)) {
                            comparator = new CityFoundationDateComparator();
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
                    } else {
                        System.out.println("Сортировка для " + currentType + " пока не реализована.");
                    }
                    if (comparator != null) {
                        collection.sortByStrategy(sortStrategy, comparator);
                        isSorted = true;
                    }
                    break;

                case "3":
                    searchStrategy = chooseSearchStrategy(scanner);
                    if (searchStrategy == null) break;

                    if ("1".equals(searchStrategy) && !isSorted) {
                        System.out.println("Сначала отсортируйте коллекцию!");
                    } else {
                        System.out.println("Введите параметры искомого объекта...");
                        <?> keyObject = new Object();

                        collection.searchByStrategy(searchStrategy, keyObject, comparator);
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
        System.out.println("1 - Сортировка слиянием (Merge Sort)");
        String type = scanner.nextLine();
        switch (type) {
            case "1": return new MergeSortStrategy();
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
}
