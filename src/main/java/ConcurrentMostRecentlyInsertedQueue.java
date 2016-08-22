import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;

public class ConcurrentMostRecentlyInsertedQueue<E> extends AbstractQueue<E> {
    private final MostRecentlyInsertedQueue<E> queue;

    public ConcurrentMostRecentlyInsertedQueue(int capacity) {
            this.queue = new MostRecentlyInsertedQueue<E>(capacity);
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

    public synchronized E remove() {
        return queue.remove();
    }

    public static void main(String[] args) {

    }
}
