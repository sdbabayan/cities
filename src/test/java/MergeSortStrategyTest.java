import data.repository.ArrayListToSortByStrategy;
import domain.sort.MergeSortStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortStrategyTest {

    private MergeSortStrategy<Integer> mergeSortStrategy;
    private Comparator<Integer> integerComparator;
    private ArrayListToSortByStrategy<Integer> listToSort;

    @BeforeEach
    void setUp() {
        mergeSortStrategy = new MergeSortStrategy<>();
        integerComparator = Comparator.naturalOrder();
        listToSort = new ArrayListToSortByStrategy<>();
    }

    @AfterEach
    void tearDown() {
        mergeSortStrategy.shutdown();
    }

    @Test
    @DisplayName("Should sort empty list")
    void shouldSortEmptyList() {
        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertTrue(result.isEmpty());
        assertTrue(listToSort.isEmpty());
    }

    @Test
    @DisplayName("Should sort single element list")
    void shouldSortSingleElementList() {
        // Given
        listToSort.add(42);
        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(List.of(42));

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(1, result.size());
        assertEquals(expected, result);
        assertEquals(42, result.get(0)); // если есть метод get(int index)
    }

    @Test
    @DisplayName("Should sort already sorted list")
    void shouldSortAlreadySortedList() {
        // Given
        listToSort.add(1);
        listToSort.add(2);
        listToSort.add(3);
        listToSort.add(4);
        listToSort.add(5);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(1, 2, 3, 4, 5)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort reverse sorted list")
    void shouldSortReverseSortedList() {
        // Given
        listToSort.add(5);
        listToSort.add(4);
        listToSort.add(3);
        listToSort.add(2);
        listToSort.add(1);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(1, 2, 3, 4, 5)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort random list")
    void shouldSortRandomList() {
        // Given
        listToSort.add(3);
        listToSort.add(1);
        listToSort.add(4);
        listToSort.add(1);
        listToSort.add(5);
        listToSort.add(9);
        listToSort.add(2);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(1, 1, 2, 3, 4, 5, 9)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort list with duplicates")
    void shouldSortListWithDuplicates() {
        // Given
        listToSort.add(5);
        listToSort.add(2);
        listToSort.add(5);
        listToSort.add(1);
        listToSort.add(2);
        listToSort.add(1);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(1, 1, 2, 2, 5, 5)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort with custom comparator")
    void shouldSortWithCustomComparator() {
        // Given
        listToSort.add(10);
        listToSort.add(5);
        listToSort.add(20);
        listToSort.add(1);

        // Reverse order comparator
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(20, 10, 5, 1)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, reverseComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort negative numbers")
    void shouldSortNegativeNumbers() {
        // Given
        listToSort.add(-3);
        listToSort.add(-1);
        listToSort.add(-5);
        listToSort.add(-2);
        listToSort.add(-4);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(-5, -4, -3, -2, -1)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should sort mixed positive and negative numbers")
    void shouldSortMixedPositiveAndNegativeNumbers() {
        // Given
        listToSort.add(-2);
        listToSort.add(5);
        listToSort.add(-1);
        listToSort.add(3);
        listToSort.add(0);
        listToSort.add(-4);

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(-4, -2, -1, 0, 3, 5)
        );

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should return new instance and not modify original")
    void shouldReturnNewInstanceAndNotModifyOriginal() {
        // Given
        listToSort.add(3);
        listToSort.add(1);
        listToSort.add(2);
        ArrayListToSortByStrategy<Integer> originalCopy = new ArrayListToSortByStrategy<>(listToSort);

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertNotSame(listToSort, result, "Should return new instance");
        assertEquals(originalCopy, listToSort, "Original list should not be modified");

        ArrayListToSortByStrategy<Integer> expected = new ArrayListToSortByStrategy<>(
                List.of(1, 2, 3)
        );
        assertEquals(expected, result, "Result should be sorted");
    }

    @Test
    @DisplayName("Should maintain list size after sorting")
    void shouldMaintainListSizeAfterSorting() {
        // Given
        int originalSize = 10;
        for (int i = 0; i < originalSize; i++) {
            listToSort.add((int) (Math.random() * 100));
        }
        int originalListSize = listToSort.size();

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(originalSize, result.size());
        assertEquals(originalListSize, listToSort.size());
    }

    @Test
    @DisplayName("Should verify sorting order for large list")
    void shouldVerifySortingOrderForLargeList() {
        // Given
        for (int i = 1000; i >= 1; i--) {
            listToSort.add(i);
        }

        // When
        ArrayListToSortByStrategy<Integer> result = mergeSortStrategy.sort(listToSort, integerComparator);

        // Then
        assertEquals(1000, result.size());

        // Проверяем что отсортировано по возрастанию
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i) <= result.get(i + 1),
                    "Elements should be in ascending order at positions " + i + " and " + (i + 1));
        }
    }
}