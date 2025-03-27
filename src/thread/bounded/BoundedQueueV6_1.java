package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoundedQueueV6_1 implements BoundedQueue {

    private BlockingQueue<String> queue;

    public BoundedQueueV6_1(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            // 인터럽트 가능
            throw new RuntimeException(e);
        }
    }

    @Override
    public String take() {
        String data;
        try {
            data = queue.take();
        } catch (InterruptedException e) {
            // 인터럽트 가능
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
