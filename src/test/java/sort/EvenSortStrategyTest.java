package sort;

import data.repository.ArrayListToSortByStrategy;
import domain.interfaces.SortStrategy;
import domain.sort.BubbleSortStrategy;
import domain.sort.EvenSortStrategy;
import domain.sort.MergeSortStrategy;
import model.TestIntObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class EvenSortStrategyTest {

    private EvenSortStrategy evenSortStrategy;
    private SortStrategy<TestIntObject> mergeSortStrategy;
    private Comparator<TestIntObject> valueComparator;

    @BeforeEach
    void setUp() {
        evenSortStrategy = new EvenSortStrategy();
        mergeSortStrategy = new MergeSortStrategy<>();
        valueComparator = Comparator.comparingInt(TestIntObject::getIntValue);
    }

    @AfterEach
    void tearDown() {
        if (mergeSortStrategy instanceof MergeSortStrategy) {
            ((MergeSortStrategy<TestIntObject>) mergeSortStrategy).shutdown();
        }
    }

    @Test
    @DisplayName("Should sort only even numbers and keep odd at original positions")
    void shouldSortOnlyEvenNumbersAndKeepOddAtOriginalPositions() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(5, "A"));  // odd - stays
        list.add(new TestIntObject(2, "B"));  // even - moves
        list.add(new TestIntObject(8, "C"));  // even - moves
        list.add(new TestIntObject(3, "D"));  // odd - stays
        list.add(new TestIntObject(4, "E"));  // even - moves

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then
        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>();
        expected.add(new TestIntObject(5, "A"));  // odd - original position
        expected.add(new TestIntObject(2, "B"));  // even - sorted
        expected.add(new TestIntObject(4, "E"));  // even - sorted
        expected.add(new TestIntObject(3, "D"));  // odd - original position
        expected.add(new TestIntObject(8, "C"));  // even - sorted

        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Should handle all odd numbers - no changes")
    void shouldHandleAllOddNumbersNoChanges() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(7, "A"));
        list.add(new TestIntObject(3, "B"));
        list.add(new TestIntObject(1, "C"));
        list.add(new TestIntObject(9, "D"));

        ArrayListToSortByStrategy<TestIntObject> original = new ArrayListToSortByStrategy<>(list);

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then - все нечетные, порядок не должен измениться
        assertEquals(original, list);
    }

    @Test
    @DisplayName("Should handle all even numbers - full sort")
    void shouldHandleAllEvenNumbersFullSort() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(8, "A"));
        list.add(new TestIntObject(2, "B"));
        list.add(new TestIntObject(6, "C"));
        list.add(new TestIntObject(4, "D"));

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then - все четные, должны быть полностью отсортированы
        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>();
        expected.add(new TestIntObject(2, "B"));
        expected.add(new TestIntObject(4, "D"));
        expected.add(new TestIntObject(6, "C"));
        expected.add(new TestIntObject(8, "A"));

        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Should handle empty list")
    void shouldHandleEmptyList() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Should handle single even element")
    void shouldHandleSingleEvenElement() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(4, "A"));

        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>(list);

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then
        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Should handle single odd element")
    void shouldHandleSingleOddElement() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(3, "A"));

        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>(list);

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then
        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Should maintain odd positions exactly")
    void shouldMaintainOddPositionsExactly() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(1, "A"));  // odd at pos 0
        list.add(new TestIntObject(4, "B"));  // even
        list.add(new TestIntObject(3, "C"));  // odd at pos 2
        list.add(new TestIntObject(2, "D"));  // even
        list.add(new TestIntObject(5, "E"));  // odd at pos 4

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then - проверяем что нечетные остались на своих позициях
        assertEquals(1, list.get(0).getIntValue()); // odd stays
        assertEquals(3, list.get(2).getIntValue()); // odd stays
        assertEquals(5, list.get(4).getIntValue()); // odd stays

        // Проверяем что четные отсортированы
        assertEquals(2, list.get(1).getIntValue()); // even sorted
        assertEquals(4, list.get(3).getIntValue()); // even sorted
    }

    @Test
    @DisplayName("Should sort even numbers in natural order")
    void shouldSortEvenNumbersInNaturalOrder() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(10, "A"));
        list.add(new TestIntObject(2, "B"));
        list.add(new TestIntObject(7, "C"));  // odd
        list.add(new TestIntObject(6, "D"));
        list.add(new TestIntObject(4, "E"));

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then - четные должны быть отсортированы по возрастанию
        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>();
        expected.add(new TestIntObject(2, "B"));
        expected.add(new TestIntObject(6, "D"));
        expected.add(new TestIntObject(7, "C"));  // odd stays
        expected.add(new TestIntObject(4, "E"));
        expected.add(new TestIntObject(10, "A"));

        // Проверяем порядок четных чисел
        assertEquals(2, list.get(0).getIntValue());  // smallest even
        assertEquals(4, list.get(1).getIntValue());  // middle even
        assertEquals(7, list.get(2).getIntValue());  // odd stays
        assertEquals(6, list.get(3).getIntValue());  // next even
        assertEquals(10, list.get(4).getIntValue()); // largest even
    }

    @Test
    @DisplayName("Should work with different sort strategies")
    void shouldWorkWithDifferentSortStrategies() {
        // Given - используем другую стратегию сортировки
        SortStrategy<TestIntObject> bubbleSortStrategy = new BubbleSortStrategy<>();
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(8, "A"));
        list.add(new TestIntObject(2, "B"));
        list.add(new TestIntObject(5, "C"));  // odd
        list.add(new TestIntObject(4, "D"));

        // When
        evenSortStrategy.sortEven(list, bubbleSortStrategy, valueComparator);

        // Then
        ArrayListToSortByStrategy<TestIntObject> expected = new ArrayListToSortByStrategy<>();
        expected.add(new TestIntObject(2, "B"));
        expected.add(new TestIntObject(4, "D"));
        expected.add(new TestIntObject(5, "C"));  // odd stays
        expected.add(new TestIntObject(8, "A"));

        assertEquals(expected, list);
    }

    @Test
    @DisplayName("Should handle negative even and odd numbers")
    void shouldHandleNegativeEvenAndOddNumbers() {
        // Given
        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(new TestIntObject(-3, "A"));  // odd
        list.add(new TestIntObject(-2, "B"));  // even
        list.add(new TestIntObject(-4, "C"));  // even
        list.add(new TestIntObject(-1, "D"));  // odd
        list.add(new TestIntObject(-6, "E"));  // even

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then
        assertEquals(-3, list.get(0).getIntValue());  // odd stays
        assertEquals(-6, list.get(1).getIntValue());  // even sorted (smallest)
        assertEquals(-4, list.get(2).getIntValue());  // even sorted
        assertEquals(-1, list.get(3).getIntValue());  // odd stays
        assertEquals(-2, list.get(4).getIntValue());  // even sorted (largest)
    }

    @Test
    @DisplayName("Should preserve object identity for odd numbers")
    void shouldPreserveObjectIdentityForOddNumbers() {
        // Given
        TestIntObject odd1 = new TestIntObject(3, "Odd1");
        TestIntObject even1 = new TestIntObject(4, "Even1");
        TestIntObject odd2 = new TestIntObject(7, "Odd2");
        TestIntObject even2 = new TestIntObject(2, "Even2");

        ArrayListToSortByStrategy<TestIntObject> list = new ArrayListToSortByStrategy<>();
        list.add(odd1);
        list.add(even1);
        list.add(odd2);
        list.add(even2);

        // When
        evenSortStrategy.sortEven(list, mergeSortStrategy, valueComparator);

        // Then - проверяем что объекты нечетных чисел те же самые
        assertSame(odd1, list.get(0));
        assertSame(odd2, list.get(2));
    }
}