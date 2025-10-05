import data.repository.ArrayListToSortByStrategy;
import domain.search.BinarySearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BinarySearchStrategyTest {

    private BinarySearchStrategy<Integer> binarySearchStrategy;
    private Comparator<Integer> integerComparator;
    private ArrayListToSortByStrategy<Integer> sortedList;
    private ArrayListToSortByStrategy<Integer> emptyList;
    private ArrayListToSortByStrategy<Integer> singleElementList;

    @BeforeEach
    void setUp() {
        binarySearchStrategy = new BinarySearchStrategy<>();
        integerComparator = Comparator.naturalOrder();

        // Подготовка отсортированного списка для бинарного поиска
        sortedList = new ArrayListToSortByStrategy<>();
        sortedList.add(1);
        sortedList.add(3);
        sortedList.add(5);
        sortedList.add(7);
        sortedList.add(9);
        sortedList.add(11);

        emptyList = new ArrayListToSortByStrategy<>();

        singleElementList = new ArrayListToSortByStrategy<>();
        singleElementList.add(42);
    }

    @Test
    @DisplayName("Should find existing element in middle")
    void shouldFindExistingElementInMiddle() {
        // Given
        Integer key = 5;

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should find existing element at beginning")
    void shouldFindExistingElementAtBeginning() {
        // Given
        Integer key = 1;

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should find existing element at end")
    void shouldFindExistingElementAtEnd() {
        // Given
        Integer key = 11;

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertEquals(11, result);
    }

    @Test
    @DisplayName("Should return null when element not found")
    void shouldReturnNullWhenElementNotFound() {
        // Given
        Integer key = 6; // Элемента нет в списке

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null for empty list")
    void shouldReturnNullForEmptyList() {
        // Given
        Integer key = 5;

        // When
        Integer result = binarySearchStrategy.search(emptyList, key, integerComparator);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should find element in single element list")
    void shouldFindElementInSingleElementList() {
        // Given
        Integer key = 42;

        // When
        Integer result = binarySearchStrategy.search(singleElementList, key, integerComparator);

        // Then
        assertEquals(42, result);
    }

    @Test
    @DisplayName("Should return null for wrong element in single element list")
    void shouldReturnNullForWrongElementInSingleElementList() {
        // Given
        Integer key = 100;

        // When
        Integer result = binarySearchStrategy.search(singleElementList, key, integerComparator);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should find duplicate elements")
    void shouldFindDuplicateElements() {
        // Given
        ArrayListToSortByStrategy<Integer> listWithDuplicates = new ArrayListToSortByStrategy<>();
        listWithDuplicates.add(1);
        listWithDuplicates.add(2);
        listWithDuplicates.add(2);
        listWithDuplicates.add(2);
        listWithDuplicates.add(3);
        Integer key = 2;

        // When
        Integer result = binarySearchStrategy.search(listWithDuplicates, key, integerComparator);

        // Then - должен найти один из дубликатов
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should work with custom comparator")
    void shouldWorkWithCustomComparator() {
        // Given
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        ArrayListToSortByStrategy<Integer> reverseSortedList = new ArrayListToSortByStrategy<>();
        reverseSortedList.add(11);
        reverseSortedList.add(9);
        reverseSortedList.add(7);
        reverseSortedList.add(5);
        reverseSortedList.add(3);
        reverseSortedList.add(1);
        Integer key = 7;

        // When
        Integer result = binarySearchStrategy.search(reverseSortedList, key, reverseComparator);

        // Then
        assertEquals(7, result);
    }

    @Test
    @DisplayName("Should return null when searching in unsorted list")
    void shouldReturnNullWhenSearchingInUnsortedList() {
        // Given - бинарный поиск требует отсортированный список
        ArrayListToSortByStrategy<Integer> unsortedList = new ArrayListToSortByStrategy<>();
        unsortedList.add(5);
        unsortedList.add(1);
        unsortedList.add(9);
        unsortedList.add(2);
        Integer key = 2;

        // When
        Integer result = binarySearchStrategy.search(unsortedList, key, integerComparator);

        // Then - может не найти элемент в несортированном списке
        // Это ожидаемое поведение для бинарного поиска
        assertNull(result);
    }

    @Test
    @DisplayName("Should handle negative numbers")
    void shouldHandleNegativeNumbers() {
        // Given
        ArrayListToSortByStrategy<Integer> negativeList = new ArrayListToSortByStrategy<>();
        negativeList.add(-5);
        negativeList.add(-3);
        negativeList.add(-1);
        negativeList.add(0);
        negativeList.add(2);
        negativeList.add(4);
        Integer key = -3;

        // When
        Integer result = binarySearchStrategy.search(negativeList, key, integerComparator);

        // Then
        assertEquals(-3, result);
    }

    @Test
    @DisplayName("Should handle large sorted list")
    void shouldHandleLargeSortedList() {
        // Given
        ArrayListToSortByStrategy<Integer> largeList = new ArrayListToSortByStrategy<>();
        for (int i = 0; i < 1000; i++) {
            largeList.add(i * 2); // Четные числа: 0, 2, 4, ..., 1998
        }
        Integer key = 555;

        // When
        Integer result = binarySearchStrategy.search(largeList, key, integerComparator);

        // Then
        assertNull(result); // 500 нет в списке (только четные)
    }

    @Test
    @DisplayName("Should find element in large sorted list")
    void shouldFindElementInLargeSortedList() {
        // Given
        ArrayListToSortByStrategy<Integer> largeList = new ArrayListToSortByStrategy<>();
        for (int i = 0; i < 1000; i++) {
            largeList.add(i * 2); // Четные числа: 0, 2, 4, ..., 1998
        }
        Integer key = 1000;

        // When
        Integer result = binarySearchStrategy.search(largeList, key, integerComparator);

        // Then
        assertEquals(1000, result);
    }

    @Test
    @DisplayName("Should return null for key smaller than all elements")
    void shouldReturnNullForKeySmallerThanAllElements() {
        // Given
        Integer key = 0;

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null for key larger than all elements")
    void shouldReturnNullForKeyLargerThanAllElements() {
        // Given
        Integer key = 20;

        // When
        Integer result = binarySearchStrategy.search(sortedList, key, integerComparator);

        // Then
        assertNull(result);
    }
}
