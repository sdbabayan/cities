package domain.additionalTasks;

import data.repository.ArrayListToSortByStrategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class SecondTask {
    /**
     * Дополнительное задание №2.
     * Записывает всю коллекцию в файл (добавляет в конец) - для любого типа
     */
    public static <T> void writeCollectionToFile(ArrayListToSortByStrategy<T> collection,
                                                 String filename,
                                                 String collectionName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("=== " + collectionName + " (" + collection.size() + " записей) ===\n");
            for (T item : collection) {
                writer.write(item.toString());
                writer.newLine();
            }
            writer.write("=== Конец коллекции ===\n\n");
            System.out.println("Коллекция записана в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Записывает один найденный объект в файл (добавляет в конец) - для любого типа
     */
    public static <T> void writeFoundObjectToFile(T object,
                                                  String filename,
                                                  String searchInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("НАЙДЕННЫЙ ОБЪЕКТ: " + searchInfo + "\n");
            writer.write(object.toString());
            writer.newLine();
            writer.write("---\n");
            System.out.println("Найденный объект записан в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Записывает результаты поиска в файл - для любого типа
     */
    public static <T> void writeSearchResultToFile(boolean found,
                                                   T searchObject,
                                                   String filename,
                                                   String strategy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("РЕЗУЛЬТАТ ПОИСКА (" + strategy + "):\n");
            writer.write("Искомый объект: " + searchObject.toString() + "\n");
            writer.write("Результат: " + (found ? "НАЙДЕН" : "НЕ НАЙДЕН") + "\n");
            writer.write("---\n");
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    /**
     * Записывает коллекцию с кастомным форматированием - для любого типа
     */
    public static <T> void writeCollectionToFile(ArrayListToSortByStrategy<T> collection,
                                                 String filename,
                                                 String collectionName,
                                                 Function<T, String> formatter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("=== " + collectionName + " (" + collection.size() + " записей) ===\n");
            for (T item : collection) {
                writer.write(formatter.apply(item));
                writer.newLine();
            }
            writer.write("=== Конец коллекции ===\n\n");
            System.out.println("Коллекция записана в файл: " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
