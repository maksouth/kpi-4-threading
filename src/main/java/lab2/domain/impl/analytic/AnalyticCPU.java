package lab2.domain.impl.analytic;

import lab2.domain.CPUProcess;
import lab2.domain.CPUQueue;

public class AnalyticCPU extends LoggingCPU {

    private volatile int processedCount;
    private volatile int maxQueueLength;

    public AnalyticCPU(String name, CPUQueue queue) {
        super(name, queue);
    }

    public void beforeExecute(CPUProcess process) {
        if(getQueue().size() > maxQueueLength) {
            maxQueueLength = getQueue().size();
        }
    }

    public void afterExecute(CPUProcess process) {
        processedCount++;
    }

    public int getProcessedCount(){
        return processedCount;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }
}
