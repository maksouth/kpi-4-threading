package lab2.factory;

import lab2.domain.CPUProcess;
import lab2.domain.impl.DumbIntensiveProcess;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class DumbProcessFactory implements CPUProcessFactory {

    private static AtomicInteger counter = new AtomicInteger(0);

    private final int minSleepMs, maxSleepMs;

    public DumbProcessFactory(int minSleepMs, int maxSleepMs) {
        this.minSleepMs = minSleepMs;
        this.maxSleepMs = maxSleepMs;
    }

    @Override
    public CPUProcess create() {
        return new DumbIntensiveProcess(name(), sleepMs());
    }

    private String name() {
        return "dumb-task-" + counter.getAndIncrement();
    }

    private int sleepMs() {
        return ThreadLocalRandom.current().nextInt(minSleepMs, maxSleepMs);
    }
}
