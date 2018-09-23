package lab2.domain.impl;

import lab2.domain.CPUProcess;

public class DumbIntensiveProcess implements CPUProcess {

    private final String name;
    private final int sleepMs;

    public DumbIntensiveProcess(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        System.out.println(TAG + " " + name + " started..");
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(TAG + " " + name + " finished");
    }

    @Override
    public String toString() {
        return name;
    }
}
