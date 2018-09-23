package lab2;

import lab2.domain.impl.analytic.AnalyticProcessDispatcher;

public class ConsoleView implements View {

    @Override
    public void showAnalytics(AnalyticProcessDispatcher.Analytics analytics) {

        System.out.println();

        printLineSeparator();

        System.out.println("TOTAL PROCESSED: " + analytics.getProcessCount());

        printLineSeparator();

        System.out.printf("%-10s", "CPU");
        System.out.printf("%-12s", "MAX QUEUE");
        System.out.printf("%-12s", "PROCESSED");
        System.out.printf("%-7s", "PERCENT\n");

        printLineSeparator();

        for (AnalyticProcessDispatcher.Entry entry : analytics.getEntries()) {
            System.out.printf("%-10s", entry.name);
            System.out.printf("%-12d", entry.maxProcessQueueLength);
            System.out.printf("%-12d", entry.processedCount);
            System.out.printf("%-7.4f", ((double) entry.processedCount)/analytics.getProcessCount());
            System.out.println();
        }

        System.out.println();
    }

    private void printLineSeparator() {
        for (int i = 0; i < 41; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
