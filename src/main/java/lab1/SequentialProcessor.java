package lab1;

public class SequentialProcessor implements Processor {

    public double scalarMultiplyVectors(double[] first, double[] second) {
        double result = 0.0;

        for (int i = 0; i < first.length; i++) {
            result += first[i] * second[i];
        }

        return result;
    }

}
