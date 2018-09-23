package lab2.domain.impl;

import lab2.domain.CPUProcess;
import lab2.domain.CPUQueue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CPUArrayQueue implements CPUQueue {

    private CPUProcess[] processes;
    private int count;
    private int head;
    private int tail;

    public CPUArrayQueue(int maxLength) {
        processes = new CPUProcess[maxLength];
    }

    public synchronized boolean offer(CPUProcess cpuProcess) {
        if(isFull())
            return false;

        doPut(cpuProcess);
        return true;
    }

    public synchronized CPUProcess poll() {
        if(isEmpty())
            return null;
        else return doTake();
    }

    private synchronized boolean isFull() {
        return count == processes.length;
    }

    private synchronized boolean isEmpty() {
        return count == 0;
    }

    private synchronized void doPut(CPUProcess cpuProcess) {
        processes[tail] = cpuProcess;

        if(++tail == processes.length)
            tail = 0;

        ++count;
    }

    private synchronized CPUProcess doTake() {
        CPUProcess process =  processes[head];
        processes[head] = null;

        if (++head == processes.length)
            head = 0;
        --count;

        return process;
    }


    @Override
    public int size() {
        return count;
    }

    @Override
    public String toString() {
        return Arrays.toString(processes);
    }
}
