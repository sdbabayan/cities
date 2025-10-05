package search;

import domain.search.ConcurrentElementCounter;
import model.TestIntObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentElementCounterTest {

    @Test
    @DisplayName("Should count occurrences in empty collection")
    void shouldCountOccurrencesInEmptyCollection() {
        // Given
        Collection<Integer> emptyCollection = new ArrayList<>();
        Integer target = 5;

        // When
        long count = ConcurrentElementCounter.countOccurrences(emptyCollection, target);

        // Then
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Should count single occurrence")
    void shouldCountSingleOccurrence() {
        // Given
        Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5);
        Integer target = 3;

        // When
        long count = ConcurrentElementCounter.countOccurrences(collection, target);

        // Then
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should count multiple occurrences")
    void shouldCountMultipleOccurrences() {
        // Given
        Collection<Integer> collection = Arrays.asList(1, 2, 3, 2, 4, 2, 5, 2);
        Integer target = 2;

        // When
        long count = ConcurrentElementCounter.countOccurrences(collection, target);

        // Then
        assertEquals(4, count);
    }

    @Test
    @DisplayName("Should return zero when target not found")
    void shouldReturnZeroWhenTargetNotFound() {
        // Given
        Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 5);
        Integer target = 99;

        // When
        long count = ConcurrentElementCounter.countOccurrences(collection, target);

        // Then
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Should count occurrences with null target")
    void shouldCountOccurrencesWithNullTarget() {
        // Given
        Collection<Integer> collection = Arrays.asList(1, null, 3, null, 5, null);
        Integer target = null;

        // When
        long count = ConcurrentElementCounter.countOccurrences(collection, target);

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Should count occurrences in large collection")
    void shouldCountOccurrencesInLargeCollection() {
        // Given
        int size = 100000;
        List<Integer> largeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            largeList.add(i % 100); // Numbers 0-99 repeated
        }
        Integer target = 42;

        // When
        long count = ConcurrentElementCounter.countOccurrences(largeList, target);

        // Then
        assertEquals(1000, count); // 100000 / 100 = 1000 occurrences
    }

    @Test
    @DisplayName("Should work with different collection types - ArrayList")
    void shouldWorkWithArrayList() {
        // Given
        Collection<String> arrayList = new ArrayList<>(Arrays.asList("a", "b", "a", "c", "a"));
        String target = "a";

        // When
        long count = ConcurrentElementCounter.countOccurrences(arrayList, target);

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Should work with different collection types - LinkedList")
    void shouldWorkWithLinkedList() {
        // Given
        Collection<String> linkedList = new LinkedList<>(Arrays.asList("x", "y", "x", "z"));
        String target = "x";

        // When
        long count = ConcurrentElementCounter.countOccurrences(linkedList, target);

        // Then
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should work with different collection types - HashSet")
    void shouldWorkWithHashSet() {
        // Given
        Collection<Integer> hashSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Integer target = 3;

        // When
        long count = ConcurrentElementCounter.countOccurrences(hashSet, target);

        // Then
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should work with concurrent collections")
    void shouldWorkWithConcurrentCollections() {
        // Given
        Collection<Integer> concurrentQueue = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 1, 3, 1, 4));
        Integer target = 1;

        // When
        long count = ConcurrentElementCounter.countOccurrences(concurrentQueue, target);

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Should handle custom objects")
    void shouldHandleCustomObjects() {
        // Given
        Collection<TestIntObject> people = Arrays.asList(
                new TestIntObject("Alice", 25),
                new TestIntObject("Bob", 30),
                new TestIntObject("Alice", 25), // Duplicate
                new TestIntObject("Charlie", 35)
        );
        TestIntObject target = new TestIntObject("Alice", 25);

        // When
        long count = ConcurrentElementCounter.countOccurrences(people, target);

        // Then
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should count string occurrences case sensitively")
    void shouldCountStringOccurrencesCaseSensitively() {
        // Given
        Collection<String> strings = Arrays.asList("Apple", "apple", "APPLE", "banana", "apple");
        String target = "apple";

        // When
        long count = ConcurrentElementCounter.countOccurrences(strings, target);

        // Then
        assertEquals(2, count); // Only lowercase "apple"
    }

    @Test
    @DisplayName("Should handle empty target in non-empty collection")
    void shouldHandleEmptyTargetInNonEmptyCollection() {
        // Given
        Collection<String> strings = Arrays.asList("a", "b", "", "c", "", "d");
        String target = "";

        // When
        long count = ConcurrentElementCounter.countOccurrences(strings, target);

        // Then
        assertEquals(2, count);
    }

    @Test
    @DisplayName("Should be thread safe")
    void shouldBeThreadSafe() throws InterruptedException, ExecutionException {
        // Given
        int numberOfThreads = 10;
        int collectionSize = 1000;
        List<Integer> largeList = new ArrayList<>();
        for (int i = 0; i < collectionSize; i++) {
            largeList.add(i % 10); // Numbers 0-9 repeated
        }
        Integer target = 5;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Long>> futures = new ArrayList<>();

        // Expected count: 1000 / 10 = 100 occurrences
        long expectedCount = 100;

        // When - запускаем подсчет в нескольких потоках одновременно
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(() ->
                    ConcurrentElementCounter.countOccurrences(largeList, target)
            ));
        }

        // Then - все потоки должны вернуть одинаковый корректный результат
        for (Future<Long> future : futures) {
            assertEquals(expectedCount, future.get());
        }

        executor.shutdown();
    }

    @Test
    @DisplayName("Should handle concurrent modifications gracefully")
    void shouldHandleConcurrentModificationsGracefully() throws InterruptedException {
        // Given
        List<Integer> modifiableList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Integer target = 3;

        // When - запускаем подсчет в отдельном потоке и модифицируем список
        Thread countingThread = new Thread(() -> {
            long count = ConcurrentElementCounter.countOccurrences(modifiableList, target);
            // Результат может быть любым из-за конкурентной модификации
            assertTrue(count >= 0 && count <= 1);
        });

        Thread modifyingThread = new Thread(() -> {
            modifiableList.add(6);
            modifiableList.remove(0);
        });

        // Then - метод не должен падать с исключением
        assertDoesNotThrow(() -> {
            countingThread.start();
            modifyingThread.start();
            countingThread.join();
            modifyingThread.join();
        });
    }

    @Test
    @DisplayName("Should count occurrences with negative numbers")
    void shouldCountOccurrencesWithNegativeNumbers() {
        // Given
        Collection<Integer> numbers = Arrays.asList(-1, -2, -1, -3, 0, 1, -1, 2);
        Integer target = -1;

        // When
        long count = ConcurrentElementCounter.countOccurrences(numbers, target);

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Should handle very large collections efficiently")
    void shouldHandleVeryLargeCollectionsEfficiently() {
        // Given
        int size = 1000000;
        List<Integer> veryLargeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            veryLargeList.add(42); // All elements are the same
        }
        Integer target = 42;

        // When - измеряем время выполнения
        long startTime = System.currentTimeMillis();
        long count = ConcurrentElementCounter.countOccurrences(veryLargeList, target);
        long endTime = System.currentTimeMillis();

        // Then
        assertEquals(size, count);
        long duration = endTime - startTime;

        // Должен обработать за разумное время (менее 1 секунды)
        assertTrue(duration < 1000, "Should process 1M elements in less than 1 second, took: " + duration + "ms");
        System.out.println("Processed 1,000,000 elements in " + duration + "ms");
    }

    @Test
    @DisplayName("Should work with arrays using Arrays.asList")
    void shouldWorkWithArraysUsingArraysAsList() {
        // Given
        Integer[] array = {1, 2, 3, 2, 4, 2, 5};
        Collection<Integer> collection = Arrays.asList(array);
        Integer target = 2;

        // When
        long count = ConcurrentElementCounter.countOccurrences(collection, target);

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("Should handle objects with custom equals method")
    void shouldHandleObjectsWithCustomEqualsMethod() {
        // Given
        class CustomObject {
            final int id;
            final String name;

            CustomObject(int id, String name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                CustomObject that = (CustomObject) obj;
                return id == that.id; // Only compare by id
            }

            @Override
            public int hashCode() {
                return Objects.hash(id);
            }
        }

        Collection<CustomObject> objects = Arrays.asList(
                new CustomObject(1, "Alice"),
                new CustomObject(2, "Bob"),
                new CustomObject(1, "Alice"), // Different object, same id
                new CustomObject(3, "Charlie")
        );
        CustomObject target = new CustomObject(1, "Someone");

        // When
        long count = ConcurrentElementCounter.countOccurrences(objects, target);

        // Then
        assertEquals(2, count); // Two objects with id = 1
    }
}