import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MostRecentlyInsertedBlockingQueueTest {

    private Queue<Integer> q;

    @Before
    public void setUp() throws Exception {
        q = new MostRecentlyInsertedBlockingQueue<>(150);
    }

    @Test
    public void testConsumerGetsAllElementsFromProducerThreads() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            es.execute(new Runnable() {
                public void run() {
                    for (int i = 1; i < 11; i++) {
                        try {
                            Thread.sleep(new Random().nextInt(100));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        q.offer(i);
                    }
                }
            });
        }
        es.shutdown();
        boolean finished = es.awaitTermination(2, TimeUnit.SECONDS);

        for (Integer i : q) {
            assertEquals(150, q.size());
        }
    }
}
