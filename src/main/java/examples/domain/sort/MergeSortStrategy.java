package examples.domain.sort;

import examples.data.repository.ArrayListToSortByStrategy;
import examples.domain.interfaces.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSortStrategy<T> implements SortStrategy<T> {
    private final ExecutorService executor;

    public MergeSortStrategy() {
        this.executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public ArrayListToSortByStrategy<T> sort(ArrayListToSortByStrategy<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) {
            return new ArrayListToSortByStrategy<T>(list);
        }

        try {
            List<T> sorted = parallelMergeSort(new ArrayList<T>(list), comparator, 0);
            return new ArrayListToSortByStrategy<>(sorted);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sorting was interrupted", e);
        }
    }

    private List<T> parallelMergeSort(List<T> list, Comparator<T> comparator, int depth) throws InterruptedException, ExecutionException {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<T> left = list.subList(0, mid);
        List<T> right = list.subList(mid, list.size());

        if (depth == 0) {
            Future<List<T>> leftFuture = executor.submit(() -> parallelMergeSort(left, comparator, depth + 1));
            Future<List<T>> rightFuture = executor.submit(() -> parallelMergeSort(right, comparator, depth + 1));

            List<T> leftResult = leftFuture.get();
            List<T> rightResult = rightFuture.get();

            return merge(leftResult, rightResult, comparator);
        } else {
            List<T> sortedLeft = parallelMergeSort(left, comparator, depth + 1);
            List<T> sortedRight = parallelMergeSort(right, comparator, depth + 1);
            return merge(sortedLeft, sortedRight, comparator);
        }
    }

    private List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    // Метод для закрытия пула потоков (важно вызывать при завершении работы)
    public void shutdown() {
        executor.shutdown();
    }
}
