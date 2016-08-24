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
                            throw new RuntimeException();
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

    @Test
    public void testQueueBlocksThreadsOnTakeWhenEmpty() throws InterruptedException {
        MostRecentlyInsertedBlockingQueue<String> q = new MostRecentlyInsertedBlockingQueue<>(1);

        //This thread remains blocked when trying to call take(...)
        //on empty queue
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    q.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        Thread.sleep(100);
        assertEquals(Thread.State.valueOf("WAITING"), t1.getState());
    }

    @Test
    public void testQueueBlocksThreadsOnPollWithTimeoutWhenEmpty() throws InterruptedException {
        MostRecentlyInsertedBlockingQueue<String> q = new MostRecentlyInsertedBlockingQueue<>(1);

        //This thread remains blocked when trying to call poll()
        //on empty queue
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    q.poll(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        Thread.sleep(100);

        assertEquals(Thread.State.valueOf("TIMED_WAITING"), t1.getState());
    }

    @Test
    public void testTakeGetsElementWhenQueueBecomesNotEmpty() throws InterruptedException {
        MostRecentlyInsertedBlockingQueue<String> q = new MostRecentlyInsertedBlockingQueue<>(1);

        //This thread remains blocked when trying to call take(...)
        //on empty queue
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String e = q.take();
                    assertEquals("PES", e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();

        Thread.sleep(100);
        q.put("PES");
        assertEquals(1, q.size());

        Thread.sleep(100);
        assertEquals(0, q.size());
    }
}
