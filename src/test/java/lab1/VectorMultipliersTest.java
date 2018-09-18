package lab1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VectorMultipliersTest {

    private VectorGenerator vectorGenerator;

    @Before
    public void setUp() {
        vectorGenerator = new SequentialVectorGenerator();
    }

    @Test
    public void testSequentialMultiplier() throws InterruptedException {
        double[] first = vectorGenerator.generate(9, 1, 1);
        double[] second = vectorGenerator.generate(9, 9, -1);
        Processor processor = new SequentialProcessor();

        Assert.assertEquals(0, Double.compare(165.0, processor.scalarMultiplyVectors(first, second)));
    }

    @Test
    public void testParallelMultiplier() throws InterruptedException {
        double[] first = vectorGenerator.generate(9, 1, 1);
        double[] second = vectorGenerator.generate(9, 9, -1);
        Processor processor = new ParallelProcessor(4);

        Assert.assertEquals(0, Double.compare(165.0, processor.scalarMultiplyVectors(first, second)));
    }

    @Test
    public void testMultiplyBigVectorsSequentially() throws InterruptedException {
        double[] first = vectorGenerator.generate(10000, 1, 0);
        double[] second = vectorGenerator.generate(10000, 1, 1);
        Processor processor = new SequentialProcessor();

        Assert.assertEquals(0, Double.compare(50005000.0, processor.scalarMultiplyVectors(first, second)));
    }

    @Test
    public void testMultiplyBigVectorsParallel() throws InterruptedException {
        double[] first = vectorGenerator.generate(10000, 1, 0);
        double[] second = vectorGenerator.generate(10000, 1, 1);
        Processor processor = new ParallelProcessor(Runtime.getRuntime().availableProcessors());

        Assert.assertEquals(0, Double.compare(50005000.0, processor.scalarMultiplyVectors(first, second)));
    }

    @Test
    public void compareTime2Threads() throws InterruptedException {
        final double[] first = vectorGenerator.generate(100000000, 1, 0);
        final double[] second = vectorGenerator.generate(100000000, 1, 1);
        Processor sequenceProcessor = new SequentialProcessor();
        Processor parallelProcessor = new ParallelProcessor(2);
        long startTime;
        long endTime;

        startTime = System.nanoTime();
        sequenceProcessor.scalarMultiplyVectors(first, second);
        endTime = System.nanoTime();
        System.out.println("Sequential in " + (endTime - startTime)/1000000 + " ms");

        startTime = System.nanoTime();
        parallelProcessor.scalarMultiplyVectors(first, second);
        endTime = System.nanoTime();
        System.out.println("Parallel in " + (endTime - startTime)/1000000 + " ms");
    }

    @Test
    public void compareTimeProcessorsNumberThreads() throws InterruptedException {
        final double[] first = vectorGenerator.generate(100000000, 1, 0);
        final double[] second = vectorGenerator.generate(100000000, 1, 1);
        Processor sequenceProcessor = new SequentialProcessor();
        Processor parallelProcessor = new ParallelProcessor(Runtime.getRuntime().availableProcessors());
        long startTime;
        long endTime;

        startTime = System.nanoTime();
        sequenceProcessor.scalarMultiplyVectors(first, second);
        endTime = System.nanoTime();
        System.out.println("Sequential in " + (endTime - startTime)/1000000 + " ms");

        startTime = System.nanoTime();
        parallelProcessor.scalarMultiplyVectors(first, second);
        endTime = System.nanoTime();
        System.out.println("Parallel in " + (endTime - startTime)/1000000 + " ms");
    }

}