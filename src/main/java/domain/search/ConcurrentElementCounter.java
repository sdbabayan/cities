package domain.search;

import java.util.*;

public class ConcurrentElementCounter {

    public static <T> long countOccurrences(Collection<T> collection, T target) {
        return collection.parallelStream()
                .filter(element -> Objects.equals(element, target))
                .count();
    }

    // Демонстрация работы
    public static void main(String[] args) {
        // Создаем тестовую коллекцию
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        int collectionSize = 10000;
        for (int i = 0; i < collectionSize; i++) {
            numbers.add(random.nextInt(10)); // Числа от 0 до 9
        }

        int targetNumber = 5;

        System.out.println("Размер тестовой коллекции: " + collectionSize);
        System.out.println("Ищем элемент: " + targetNumber + "\n");

        // Тестируем разные версии
        System.out.println("1. ExecutorService с Runnable и AtomicInteger:");
        long startTime = System.currentTimeMillis();
        int count1 = Math.toIntExact(countOccurrences(numbers, targetNumber));
        long endTime = System.currentTimeMillis();
        System.out.println("Результат: " + count1 + " вхождений");
        System.out.println("Время: " + (endTime - startTime) + " мс\n");

        // Проверка правильности
        int singleThreadCount = 0;
        for (Integer num : numbers) {
            if (Objects.equals(num, targetNumber)) {
                singleThreadCount++;
            }
        }

        System.out.println("=== ПРОВЕРКА ===");
        System.out.println("Однопоточный результат: " + singleThreadCount);
        System.out.println("Все многопоточные методы верны: " +
                (count1 == singleThreadCount));
    }
}
