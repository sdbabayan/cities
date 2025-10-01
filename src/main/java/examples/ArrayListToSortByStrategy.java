package examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ArrayListToSortByStrategy<T> extends ArrayList<T> {

    public ArrayListToSortByStrategy() {
        super();
    }

    public ArrayListToSortByStrategy(List<T> list) {
        super(list);
    }

    /**
     * Сортирует коллекцию с использованием компаратора
     *
     * @param comparator компаратор для сравнения элементов
     */
    public void sortByStrategy(Comparator<T> comparator) {
        this.sort(comparator);
    }

    /**
     * Бинарный поиск в отсортированной коллекции
     *
     * @param key        искомый элемент
     * @param comparator компаратор для сравнения элементов
     * @return индекс элемента или -(insertion point) - 1 если не найден
     */
    public int binarySearch(T key, Comparator<T> comparator) {
        int low = 0;
        int high = this.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = this.get(mid);
            int cmp = comparator.compare(midVal, key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // ключ найден
            }
        }
        return -(low + 1); // ключ не найден
    }

    /**
     * Бинарный поиск с возможностью фильтрации
     *
     * @param key        искомый элемент
     * @param comparator компаратор для сравнения
     * @param filter     предикат для фильтрации элементов
     * @return индекс элемента или -(insertion point) - 1 если не найден
     */
    public int binarySearch(T key, Comparator<T> comparator, Predicate<T> filter) {
        int low = 0;
        int high = this.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = this.get(mid);

            // Пропускаем элементы, не удовлетворяющие фильтру
            if (filter != null && !filter.test(midVal)) {
                // Находим ближайший элемент, удовлетворяющий фильтру
                int left = mid - 1;
                int right = mid + 1;
                boolean found = false;

                while (left >= low || right <= high) {
                    if (left >= low && filter.test(this.get(left))) {
                        mid = left;
                        midVal = this.get(left);
                        found = true;
                        break;
                    }
                    if (right <= high && filter.test(this.get(right))) {
                        mid = right;
                        midVal = this.get(right);
                        found = true;
                        break;
                    }
                    left--;
                    right++;
                }

                if (!found) {
                    return -1; // нет элементов, удовлетворяющих фильтру
                }
            }

            int cmp = comparator.compare(midVal, key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // ключ найден
            }
        }
        return -(low + 1); // ключ не найден
    }

    /**
     * Проверяет, отсортирована ли коллекция по заданному компаратору
     *
     * @param comparator компаратор для проверки
     * @return true если коллекция отсортирована
     */
    public boolean isSorted(Comparator<T> comparator) {
        for (int i = 0; i < this.size() - 1; i++) {
            if (comparator.compare(this.get(i), this.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает минимальный элемент по компаратору
     *
     * @param comparator компаратор для сравнения
     * @return минимальный элемент
     */
    public T min(Comparator<T> comparator) {
        if (this.isEmpty()) {
            return null;
        }

        T min = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (comparator.compare(this.get(i), min) < 0) {
                min = this.get(i);
            }
        }
        return min;
    }

    /**
     * Возвращает максимальный элемент по компаратору
     *
     * @param comparator компаратор для сравнения
     * @return максимальный элемент
     */
    public T max(Comparator<T> comparator) {
        if (this.isEmpty()) {
            return null;
        }

        T max = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (comparator.compare(this.get(i), max) > 0) {
                max = this.get(i);
            }
        }
        return max;
    }

    /**
     * Создает отсортированную копию коллекции
     *
     * @param comparator компаратор для сортировки
     * @return новая отсортированная коллекция
     */
    public ArrayListToSortByStrategy<T> getSortedCopy(Comparator<T> comparator) {
        ArrayListToSortByStrategy<T> copy = new ArrayListToSortByStrategy<>(this);
        copy.sortByStrategy(comparator);
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArrayListToSortByStrategy [\n");
        for (int i = 0; i < this.size(); i++) {
            sb.append("  ").append(i).append(": ").append(this.get(i));
            if (i < this.size() - 1) {
                sb.append("\n");
            }
        }
        sb.append("\n]");
        return sb.toString();
    }
}
