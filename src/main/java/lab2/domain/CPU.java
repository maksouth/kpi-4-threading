package lab2.domain;

/**
 * can be extended
 */
public class CPU extends Thread {

    protected String TAG = "[CPU] ";

    private final CPUQueue queue;
    protected final String name;

    public CPU(String name, CPUQueue queue) {
        setName(name);
        this.name = name;
        this.queue = queue;
    }

    @Override
    public final void run() {
        CPUProcess process;
        while ( (process = queue.poll()) != null) {
            if (Thread.currentThread().isInterrupted()) {
                Thread.currentThread().interrupt();
                break;
            }

            beforeExecute(process);

            process.run();

            afterExecute(process);
        }
    }

    /**
     * can be overridden
     * contract is that you should not modify parameters
     * always call super
     * only logging, analytics happens here, no logic
     */
    public void beforeExecute(CPUProcess process) {}

    /**
     * can be overridden
     * contract is that you should not modify parameters
     * always call super
     * only logging, analytics happens here, no logic
     */
    public void afterExecute(CPUProcess process) {}

    public CPUQueue getQueue() {
        return queue;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
