import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class ConcurrentMostRecentlyInsertedQueue<E> extends AbstractQueue<E> {
    private MostRecentlyInsertedQueue<E> queue;

    public ConcurrentMostRecentlyInsertedQueue(MostRecentlyInsertedQueue<E> queue) {
        this.queue = queue;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public synchronized int size() {
        return queue.size();
    }

    @Override
    public synchronized boolean offer(E e) {
        return queue.offer(e);
    }

    @Override
    public synchronized E poll() {
        return queue.poll();
    }

    @Override
    public synchronized E peek() {
        return queue.peek();
    }
}
