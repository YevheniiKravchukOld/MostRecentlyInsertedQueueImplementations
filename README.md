# MostRecentlyInsertedQueueImplementations
Implementation of test project.

## purpose
To create an implementation of java.util.Queue<E> interface, which purpose is to store N most recently inserted elements.

## features
1. The queue is bounded in size.
2. New elements are added to the tail of the queue
3. The queue is traversed from head to tail
4. Queue always accepts new elements. If queue is full, the oldest element is evicted and then a new element is added at the tail.

## behavior example
The queue behaves as follows:

```
   Queue<Integer> queue = new MostRecentlyInsertedQueue<Integer>(3);
   // queue.size(): 0, contents (head -> tail): [ ]
   queue.offer(1); // queue.size(): 1, contents (head -> tail): [ 1 ]
   queue.offer(2); // queue.size(): 2, contents (head -> tail): [ 1, 2 ]
   queue.offer(3); // queue.size(): 3, contents (head -> tail): [ 1, 2, 3 ]
   queue.offer(4); // queue.size(): 3, contents (head -> tail): [ 2, 3, 4 ]
   queue.offer(5); // queue.size(): 3, contents (head -> tail): [ 3, 4, 5 ]
   int poll1 = queue.poll(); // queue.size(): 2, contents (head -> tail): [ 4, 5 ], poll1 = 3
   int poll2 = queue.poll(); // queue.size(): 1, contents (head -> tail): [ 5 ], poll2 = 4
   queue.clear(); // queue.size(): 0, contents (head -> tail): [ ]

```

## implementations
There are three available implementatioins:
* `MostRecentlyInsertedQueue` (for single-threaded environment)
* `ConcurrentMostRecentlyInsertedQueue` (a thread-safe version of `MostRecentlyInsertedQueue`)
* `MostRecentlyInsertedBlockingQueue` (a thread-safe variant of `MostRecentlyInsertedQueue` that implements `java.util.concurrent.BlockingQueue<E>`)