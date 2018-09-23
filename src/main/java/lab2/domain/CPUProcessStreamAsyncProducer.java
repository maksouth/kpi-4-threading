package lab2.domain;

import lab2.TimerThread;
import lab2.factory.CPUProcessFactory;

import java.util.function.Consumer;

public class CPUProcessStreamAsyncProducer {

    private final CPUProcessFactory factory;
    private TimerThread timerThread;

    public CPUProcessStreamAsyncProducer(CPUProcessFactory cpuProcessFactory) {
        this.factory = cpuProcessFactory;
    }

    public void generate(int size, int minDelayMs, int maxDelayMs, Consumer<CPUProcess> consumer) {
        interruptTimerThread();
        timerThread = new TimerThread(size, minDelayMs, maxDelayMs, factory, consumer);
        timerThread.start();
    }

    public void shutdown() {
        interruptTimerThread();
    }

    private void interruptTimerThread() {
        if(timerThread != null) {
            timerThread.interrupt();
        }
    }
}

