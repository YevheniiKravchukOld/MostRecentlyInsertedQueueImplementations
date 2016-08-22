import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MostRecentlyInsertedQueueTest {

    private Queue<Integer> q;

    @Before
    public void setUp() throws Exception {
        q = new MostRecentlyInsertedQueue<>(10);
    }

    @Test
    public void testIsEmptyReturnsTrueOnEmpty() {
        assertTrue(q.isEmpty());
    }

    @Test
    public void testIsEmptyReturnsFalseOnNotEmpty() {
        q.offer(0);
        assertFalse(q.isEmpty());
    }

    @Test
    public void testElementsOrdering() {
        q.offer(1);
        q.offer(2);
        q.offer(3);
        assertEquals(Integer.valueOf(1), q.poll());
        assertEquals(Integer.valueOf(2), q.poll());
        assertEquals(Integer.valueOf(3), q.poll());
    }

    @Test
    public void testPollRetrievesCorrespondingElementOnNotEmpty() {
        q.offer(1);
        assertEquals(Integer.valueOf(1), q.poll());
    }

    @Test
    public void testPollReturnsNullOnEmpty() {
        assertNull(q.poll());
    }

    @Test
    public void testRemoveOnNotEmpty() {
        q.offer(1);
        assertEquals(Integer.valueOf(1), q.remove());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveThrowsExceptionOnEmpty() {
        q.remove();
    }

    @Test
    public void testSizeReturnsZeroOnEmpty() {
        assertEquals(0, q.size());
    }

    @Test
    public void testSizeReturnsCorrectValueOnPartlyFilled() {
        for (int i = 0; i < 5; i++) q.offer(i);
        assertEquals(5, q.size());
    }

    @Test
    public void testSizeReturnsCorrectValueOnOverfilled() {
        for (int i = 0; i < 15; i++) q.offer(i);
        assertEquals(10, q.size());
    }

    @Test
    public void testClearWorksProperlyOnNotEmpty() {
        for (int i = 0; i < 5; i++) q.offer(i);
        q.clear();
        assertTrue(q.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testOfferThrowsExceptionOnNullElement() {
        q.offer(null);
    }

    @Test
    public void testPeekReturnsNullOnEmpty() {
        assertNull(q.peek());
    }

    @Test
    public void testPeekReturnsCorrectValueOnNotEmpty() {
        q.offer(1);
        assertEquals(Integer.valueOf(1), q.peek());
    }

    @Test
    public void testPeekOnNotEmptyDoesNotRemoveElement() {
        q.offer(1);
        q.peek();
        assertEquals(1, q.size());
    }

    @Test
    public void testOfferRemovesLastRecentElementWhenQueueIsFull(){

        //oldest (head) element must be overriden,
        //when queue capacity reaches it's limit
        for(int i = 1; i < 11; i++) {
            q.offer(i);
        }
        q.offer(11);
        assertEquals(Integer.valueOf(2), q.poll());
    }

    @Test
    public void testIteratorHasNextReturnsFalseOnEmpty() {
        boolean hasNext = q.iterator().hasNext();
        assertFalse(hasNext);
    }

    @Test
    public void testIteratorHasNextReturnsTrueOnNotEmpty() {
        q.offer(1);
        boolean hasNext = q.iterator().hasNext();
        assertTrue(hasNext);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextThrowsExceptionOnEmpty() {
        q.iterator().next();
    }

    @Test
    public void testIteratorNextReturnsNextElementOnNotEmpty() {
        q.offer(1);
        q.offer(2);
        q.offer(3);
        Iterator<Integer> iterator = q.iterator();
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveThrowsIllegalStateException() {
        q.iterator().remove();
    }
}