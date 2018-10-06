package lab3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

class PredicateRunnable<T> implements Runnable {

    private final T element;
    private final Predicate<T> predicate;
    private final CountDownLatch latch;
    private final AtomicInteger counter;

    PredicateRunnable(T element,
                      Predicate<T> predicate,
                      CountDownLatch latch,
                      AtomicInteger counter) {
        this.element = element;
        this.predicate = predicate;
        this.latch = latch;
        this.counter = counter;
    }

    @Override
    public void run() {
        if (predicate.test(element)) {
            counter.getAndIncrement();
        }

        latch.countDown();
    }
}
