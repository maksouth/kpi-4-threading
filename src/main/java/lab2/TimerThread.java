package lab2;

import lab2.domain.CPUProcess;
import lab2.factory.CPUProcessFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class TimerThread extends Thread {

    private final int size;
    private final int minDelayMs;
    private final int maxDelayMs;
    private final CPUProcessFactory factory;
    private final Consumer<CPUProcess> consumer;
    private int generated;

    public TimerThread(int size,
                       int minDelayMs,
                       int maxDelayMs,
                       CPUProcessFactory cpuProcessFactory,
                       Consumer<CPUProcess> consumer) {
        this.size = size;
        this.minDelayMs = minDelayMs;
        this.maxDelayMs = maxDelayMs;
        this.consumer = consumer;
        this.factory = cpuProcessFactory;
    }

    @Override
    public void run() {
        int delay;
        while (generated < size) {
            delay = ThreadLocalRandom.current().nextInt(minDelayMs, maxDelayMs);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
                return;
            }

            consumer.accept(factory.create());
            generated++;
        }
    }
}