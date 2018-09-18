package lab1;

public class SequentialVectorGenerator implements VectorGenerator {
    public double[] generate(int size, double firstElement, double delta) {
        double[] vector = new double[size];

        vector[0] = firstElement;

        for(int i = 1; i < size; i++) {
            vector[i] = vector[i-1] + delta;
        }

        return vector;
    }
}
