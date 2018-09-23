package lab2.domain;

import com.sun.istack.internal.Nullable;

public interface CPUQueue {
    boolean offer(CPUProcess cpuProcess);

    @Nullable
    CPUProcess poll();

    int size();
}
