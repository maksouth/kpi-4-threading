package lab3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class ComparableRunnable<T extends Comparable<T>> implements Runnable {

    private final CountDownLatch latch;
    private final AtomicReference<Pair<T, Integer>> maxElement;
    private final T element;
    private final int index;

    public ComparableRunnable(CountDownLatch latch, AtomicReference<Pair<T, Integer>> maxElement, T element, int index) {
        this.latch = latch;
        this.maxElement = maxElement;
        this.element = element;
        this.index = index;
    }

    @Override
    public void run() {
        Pair<T, Integer> maxElementLocal;

        do {
            maxElementLocal = maxElement.get();
            if(element.compareTo(maxElementLocal.first()) <= 0) break;
        } while (!maxElement.compareAndSet(maxElementLocal, new Pair<>(element, index)));

        latch.countDown();
    }
}
