package lab2.factory;

import lab2.domain.CPU;
import lab2.domain.CPUQueue;
import lab2.domain.impl.CPUArrayQueue;
import lab2.domain.impl.analytic.LoggingCPU;

import java.util.concurrent.atomic.AtomicInteger;

public class CPUFactory {

    private static AtomicInteger counter = new AtomicInteger(0);

    private final int maxQueueSize;

    public CPUFactory(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public CPU create() {
        CPUQueue queue = new CPUArrayQueue(maxQueueSize);
        CPU cpu = new LoggingCPU(name(), queue);

        System.out.println("Created new CPU " + cpu);

        return cpu;
    }

    private String name() {
        return "cpu-" + counter.getAndIncrement();
    }
}
