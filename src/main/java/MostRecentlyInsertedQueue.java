import java.util.*;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E> {

    private final E[] elements;

    private final int capacity;

    private int nextHeadIndex;

    private int nextTailIndex;

    private boolean isFull;

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
        elements[nextTailIndex] = e;
        if (nextTailIndex >= capacity) nextTailIndex = 0;
        nextTailIndex = incrementIndex(nextTailIndex);
        if (nextHeadIndex == nextTailIndex) isFull = true;
        return true;
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
    public int size() {
        int size = 0;
        if (nextTailIndex < nextHeadIndex) {
            size = capacity - nextHeadIndex + nextTailIndex;
        } else if (nextHeadIndex == nextTailIndex) {
            size = isFull ? capacity : 0;
        } else {
            size = nextTailIndex - nextHeadIndex;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
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
                if(lastReturnedIndex == -1) {
                    throw new IllegalStateException();
                } else {
                    throw new UnsupportedOperationException("Operation is not supported");
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

        Queue<Integer> q = new MostRecentlyInsertedQueue<>(10);
        for(int i = 0; i < 15; i++) {
            q.offer(i);
        }
        q.offer(1);

        System.out.println(q.toString());
    }

}

