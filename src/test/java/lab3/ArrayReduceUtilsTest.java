package lab3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArrayReduceUtilsTest {

    @Test
    public void testCountByAllElementsFitPredicate() {
        int size = 1000000;
        int filler = 1;
        Integer[] integers = new Integer[size];
        Arrays.fill(integers, filler);

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayReduceUtils utils = new ArrayReduceUtils(executorService);

        int matchPredicate = utils.countBy(integers, (integer -> integer == filler));
        Assert.assertEquals(size, matchPredicate);
    }

    @Test
    public void testCountNoneElementFitPredicate() {
        int size = 1000000;
        int filler = 1;
        Integer[] integers = new Integer[size];
        Arrays.fill(integers, filler);

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayReduceUtils utils = new ArrayReduceUtils(executorService);

        int matchPredicate = utils.countBy(integers, (integer -> integer == 0));
        Assert.assertEquals(0, matchPredicate);
    }

    @Test
    public void testCountByHalfOfElementsFitPredicate() {
        int size = 1000000;
        int filler = 1;
        Integer[] integers = new Integer[size];
        Arrays.fill(integers, 0, size/2, filler);
        Arrays.fill(integers, size/2 + 1, size, 999);

        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayReduceUtils utils = new ArrayReduceUtils(executorService);

        int matchPredicate = utils.countBy(integers, (integer -> Objects.equals(integer, filler)));
        Assert.assertEquals(size/2, matchPredicate);
    }

    @Test
    public void testMaxLastIndex() {
        int size = 1000000;
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = i;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayReduceUtils utils = new ArrayReduceUtils(executorService);

        Assert.assertEquals(size - 1, utils.max(integers));
    }

    @Test
    public void testMaxAllEquals() {
        int size = 1000000;
        Integer[] integers = new Integer[size];
        Arrays.fill(integers, 222);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayReduceUtils utils = new ArrayReduceUtils(executorService);

        Assert.assertEquals(0, utils.max(integers));
    }
}