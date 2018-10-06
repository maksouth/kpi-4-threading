package lab3;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class ArrayReduceUtils {

    private final ExecutorService executorService;

    public ArrayReduceUtils(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> int countBy(T[] elements, Predicate<T> predicate) {
        CountDownLatch latch = new CountDownLatch(elements.length);
        AtomicInteger counter = new AtomicInteger();

        for (T e : elements) {
            executorService.submit(
                new PredicateRunnable<T>(e, predicate, latch, counter));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }

        return counter.get();
    }

    public <T extends Comparable<T>> int max(T[] elements) {
        CountDownLatch latch = new CountDownLatch(elements.length - 1);
        Pair<T, Integer> pair = new Pair<>(elements[0], 0);
        AtomicReference<Pair<T, Integer>> maxElement = new AtomicReference<>(pair);

        for (int i = 1; i < elements.length; i++) {
            executorService.submit(new ComparableRunnable<T>(latch, maxElement, elements[i], i));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }

        return maxElement.get().second();
    }

    public int calculateChecksum(int[] array) {
        AtomicInteger checksum = new AtomicInteger();

        Arrays.stream(array)
            .parallel()
            .forEach(element ->
                checksum.accumulateAndGet(element,
                    (sum, nextElement) -> sum ^ nextElement)
            );

        return checksum.get();
    }

}
