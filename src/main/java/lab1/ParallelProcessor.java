package lab1;

public class ParallelProcessor implements Processor {

    private final int threadsNumber;

    public ParallelProcessor(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    public double scalarMultiplyVectors(double[] first, double[] second) throws InterruptedException {
        MultiplierThread[] threads = new MultiplierThread[threadsNumber];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiplierThread(first, second,
                first.length / threadsNumber * i,
                i == threadsNumber - 1
                    ? first.length
                    : first.length / threadsNumber * (i + 1));
            threads[i].start();
        }

        for (MultiplierThread thread : threads) {
            thread.join();
        }

        double parallelResult = 0;
        for (MultiplierThread thread : threads) {
            parallelResult += thread.getResult();
        }

        return parallelResult;
    }

    private static class MultiplierThread extends Thread {

        private final double[] first;
        private final double[] second;
        private final int startIndex;
        private final int endIndex;
        private double result = 0;

        MultiplierThread(double[] first,
                         double[] second,
                         int startIndex,
                         int endIndex) {
            this.first = first;
            this.second = second;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public double getResult() {
            return result;
        }

        @Override
        public void run() {
            for(int i = startIndex; i < endIndex; i++ ){
                result += first[i] * second[i];
            }
        }
    }
}
