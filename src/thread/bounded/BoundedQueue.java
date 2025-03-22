package thread.bounded;

/**
 * Buffer 역할 Queue
 */
public interface BoundedQueue {
    void put(String data);

    String take();
}
