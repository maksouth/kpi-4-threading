package lab2.domain;

import lab2.factory.CPUFactory;

import java.util.*;

/**
 * can be extended
 */
public class ProcessDispatcher {

    protected String TAG = "[DISP] ";

    private final CPUFactory cpuFactory;
    private final List<CPU> cpuList;

    public ProcessDispatcher(CPUFactory cpuFactory) {
        cpuList = new ArrayList<>();
        this.cpuFactory = cpuFactory;
    }

    public final synchronized void addProcess(CPUProcess cpuProcess) {

        System.out.println(TAG + "Add new task " + cpuProcess);
        ListIterator<CPU> cpuListIterator = cpuList.listIterator();
        CPU cpu;

        while (cpuListIterator.hasNext()) {
            cpu = cpuListIterator.next();
            if (!cpu.isAlive()) {
                System.out.println(TAG + "Remove " + cpu + " from cpu list");
                cpuListIterator.remove();
                continue;
            }

            if(cpu.getQueue().offer(cpuProcess)) {
                System.out.println(TAG + "Added task " + cpuProcess + " to cpu " + cpu + " task queue " + cpu.getQueue());
                afterAddProcess(cpu);
                return;
            }
        }

        cpu = cpuFactory.create();
        cpuList.add(cpu);

        cpu.getQueue().offer(cpuProcess);
        afterAddProcess(cpu);
        System.out.println(TAG + "Added task " + cpuProcess + " to new cpu " + cpu + " task queue " + cpu.getQueue());

        cpu.start();
    }

    /**
     * contract do not modify cpu
     * call super
     */
    protected void afterAddProcess(CPU cpu) {}

}
