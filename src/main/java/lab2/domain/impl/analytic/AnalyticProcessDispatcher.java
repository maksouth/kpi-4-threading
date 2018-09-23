package lab2.domain.impl.analytic;

import lab2.domain.CPU;
import lab2.domain.ProcessDispatcher;
import lab2.factory.CPUFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AnalyticProcessDispatcher extends ProcessDispatcher {

    private int processCount;
    private ConcurrentMap<CPU, Integer> cpuToMaxQueueLengthMap = new ConcurrentHashMap<>();
    private ConcurrentMap<CPU, Integer> cpuToProcessedCountMap = new ConcurrentHashMap<>();

    public AnalyticProcessDispatcher(CPUFactory cpuFactory) {
        super(cpuFactory);
    }

    @Override
    protected void afterAddProcess(CPU cpu) {
        processCount++;

        cpuToMaxQueueLengthMap.putIfAbsent(cpu, cpu.getQueue().size());
        cpuToMaxQueueLengthMap.computeIfPresent(cpu,
            (_cpu, length) ->
                cpu.getQueue().size() > length
                    ? cpu.getQueue().size()
                    : length
        );

        cpuToProcessedCountMap.putIfAbsent(cpu, 0);
        cpuToProcessedCountMap.computeIfPresent(cpu, (_cpu, counter) -> ++counter);
    }

    public Analytics getAnalytics() {
        return new Analytics();
    }

    public class Analytics {

        private Analytics() {}

        public int getProcessCount() {
            return processCount;
        }

        public Set<Entry> getEntries() {
            Set<Entry> result = new TreeSet<>(Comparator.<Entry, Integer>comparing(e -> e.name.length())
                                                        .thenComparing(o -> o.name));
            Entry entry;

            for (Map.Entry<CPU, Integer> mapEntry : cpuToMaxQueueLengthMap.entrySet()) {
                entry = new Entry(mapEntry.getKey().getName(), mapEntry.getValue(), cpuToProcessedCountMap.get(mapEntry.getKey()));
                result.add(entry);
            }

            return result;
        }

    }

    public static class Entry {

        public final String name;
        public final int maxProcessQueueLength;
        public final int processedCount;

        public Entry(String name, int maxProcessQueueLength, int processedCount) {
            this.name = name;
            this.maxProcessQueueLength = maxProcessQueueLength;
            this.processedCount = processedCount;
        }
    }
}
