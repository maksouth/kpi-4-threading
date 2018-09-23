package lab2.domain.impl.analytic;

import lab2.domain.CPU;
import lab2.domain.CPUProcess;
import lab2.domain.CPUQueue;

public class LoggingCPU extends CPU {

    private long startTime;

    public LoggingCPU(String name, CPUQueue queue) {
        super(name, queue);
    }

    @Override
    public void beforeExecute(CPUProcess process) {
        System.out.println(TAG + name + " started executing process " + process);
        startTime = System.nanoTime();
    }

    public void afterExecute(CPUProcess process) {
        System.out.println(TAG + name + " executed process " + process +
            " in " + (System.nanoTime() - startTime)/1000000 + " ms");
    }
}
