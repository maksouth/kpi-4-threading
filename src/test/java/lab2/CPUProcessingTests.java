package lab2;

import lab2.domain.CPUProcessStreamAsyncProducer;
import lab2.domain.ProcessDispatcher;
import lab2.factory.CPUFactory;
import lab2.factory.CPUProcessFactory;
import lab2.factory.DumbProcessFactory;
import lab2.domain.impl.analytic.AnalyticProcessDispatcher;
import org.junit.Before;
import org.junit.Test;

public class CPUProcessingTests {

    View view;

    @Before
    public void setUp() {
        view = new ConsoleView();
    }

    @Test
    public void testCPUFastProducerSlowTasks() throws InterruptedException {
        CPUProcessFactory processFactory = new DumbProcessFactory(1000, 1500);
        CPUProcessStreamAsyncProducer streamAsyncProducer = new CPUProcessStreamAsyncProducer(processFactory);
        CPUFactory cpuFactory = new CPUFactory(2);
        ProcessDispatcher processDispatcher = new ProcessDispatcher(cpuFactory);

        streamAsyncProducer.generate(10, 10, 20, processDispatcher::addProcess);

        Thread.sleep(30000);
    }

    @Test
    public void testCPUSlowProducerSlowTasks() throws InterruptedException {
        CPUProcessFactory processFactory = new DumbProcessFactory(1000, 1500);
        CPUProcessStreamAsyncProducer streamAsyncProducer = new CPUProcessStreamAsyncProducer(processFactory);
        CPUFactory cpuFactory = new CPUFactory(2);
        ProcessDispatcher processDispatcher = new ProcessDispatcher(cpuFactory);

        streamAsyncProducer.generate(10, 800, 1300, processDispatcher::addProcess);

        Thread.sleep(30000);
    }

    @Test
    public void testCPUProducerSlowTasksSmallBuffer() throws InterruptedException {
        CPUProcessFactory processFactory = new DumbProcessFactory(1000, 1500);
        CPUProcessStreamAsyncProducer streamAsyncProducer = new CPUProcessStreamAsyncProducer(processFactory);
        CPUFactory cpuFactory = new CPUFactory(1);
        AnalyticProcessDispatcher processDispatcher = new AnalyticProcessDispatcher(cpuFactory);

        streamAsyncProducer.generate(10, 800, 1000, processDispatcher::addProcess);

        Thread.sleep(30000);
    }


    @Test
    public void testCPUShowAnalytics() throws InterruptedException {
        CPUProcessFactory processFactory = new DumbProcessFactory(10, 15);
        CPUProcessStreamAsyncProducer streamAsyncProducer = new CPUProcessStreamAsyncProducer(processFactory);
        CPUFactory cpuFactory = new CPUFactory(20);
        AnalyticProcessDispatcher processDispatcher = new AnalyticProcessDispatcher(cpuFactory);

        streamAsyncProducer.generate(1000, 2, 4, processDispatcher::addProcess);

        Thread.sleep(15000);

        view.showAnalytics(processDispatcher.getAnalytics());
    }

}