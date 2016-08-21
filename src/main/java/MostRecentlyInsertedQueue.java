import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E> {

    /**
     * Array of queue elements
     */
    private final E[] elements;

    /**
     * Queue capacity
     */
    private final int capacity;

    /**
     * Array index of oldest queue element
     */
    private int nextHeadIndex;

    /**  */
    private int nextTailIndex;

    /**
     * Indicates whether the queue is full or not
     */
    private boolean isFull;

    /** */
    public MostRecentlyInsertedQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Queue size must" +
                " be greater than 0");
        this.elements = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    @Override
    public boolean offer(E e) {
        if (null == e) throw new NullPointerException("Unable to add null object" +
                " to queue");
        if (size() == capacity) remove();
        elements[nextTailIndex++] = e;
        if (nextTailIndex >= capacity) nextTailIndex = 0;
        if (nextHeadIndex == nextTailIndex) isFull = true;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        E e = remove();
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()) return null;
        return elements[nextHeadIndex];
    }

    @Override
    public E remove() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        E e = elements[nextHeadIndex];
        if (null != e) {
            elements[nextHeadIndex] = null;
            nextHeadIndex = incrementIndex(nextHeadIndex);
            isFull = false;
        }
        return e;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        int size = 0;
        if (nextHeadIndex < nextTailIndex) {
            size = capacity - nextHeadIndex + nextTailIndex;
        } else if (nextHeadIndex == nextTailIndex) {
            size = isFull ? capacity : 0;
        } else {
            size = nextTailIndex - nextHeadIndex;
        }
        return size;
    }

    @Override
    public void clear() {
        isFull = false;
        nextHeadIndex = nextTailIndex = 0;
        Arrays.fill(elements, null);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = nextHeadIndex;
            private int lastReturnedIndex = -1;
            private boolean isFirst = isFull;

            @Override
            public boolean hasNext() {
                return isFirst || index != nextTailIndex;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                isFirst = false;
                lastReturnedIndex = index;
                index = incrementIndex(index);
                return elements[lastReturnedIndex];
            }

            @Override
            public void remove() {
                if (lastReturnedIndex == -1) {
                    throw new IllegalArgumentException();
                }

                if (lastReturnedIndex == nextHeadIndex) {
                    this.remove();
                    lastReturnedIndex = -1;
                    return;
                }

                int iteratorPosition = lastReturnedIndex + 1;
                if (nextHeadIndex < lastReturnedIndex && iteratorPosition < nextTailIndex) {

                }
            }

        };
    }

    private int incrementIndex(int index) {
        index++;
        if (index >= capacity) index = 0;
        return index;
    }

    private int decrementIndex(int index) {
        index--;
        if (index < 0) index = capacity - 1;
        return index;
    }


    public static void main(String[] args) {

        Queue<Integer> q = new MostRecentlyInsertedQueue<>(4);

        q.offer(1);
        q.offer(2);
        q.offer(3);
        q.offer(4);
        q.offer(5);
        for (Integer e : q) {
            System.out.print(e + " ");
        }
        System.out.println();
        q.offer(6);
        q.offer(7);
        q.offer(8);
        q.offer(9);

        for (Integer e : q) {
            System.out.print(e + " ");
        }

        System.out.println();
        q.peek();

        for (Integer e : q) {
            System.out.print(e + " ");
        }

        System.out.println();
        q.offer(10);

        for (Integer e : q) {
            System.out.print(e + " ");
        }
        System.out.println();

        q.poll();

        for (Integer e : q) {
            System.out.print(e + " ");
        }

        System.out.println();
    }

}

