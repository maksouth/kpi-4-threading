package lab1;

public interface Processor {
    /**
     * processor assumes, that sizes of passes vectors are equal
     * thus, does not do any checks
     */
    double scalarMultiplyVectors(double[] first, double[] second) throws InterruptedException;
}
