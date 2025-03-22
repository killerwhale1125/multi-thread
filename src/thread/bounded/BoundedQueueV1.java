package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;

/**
 * 역할 : 생산자 / 소비자의 Buffer 역할
 * 책임 : 생산자가 생산하는 인스턴스를 순서대로 잘 보관하는 책임
 */
public class BoundedQueueV1 implements BoundedQueue {

    /* Critical Section */
    private Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV1(int max) {
        this.max = max;
    }

    /**
     * 생산자 전용
     */
    @Override
    public synchronized void put(String data) {
        if (queue.size() == max) {
            log("[put] 큐가 가득 참, 버림 " + data);
            return;
        }
        // offer은 예외 X false O
        queue.offer(data);
    }

    /**
     * 소비자 전용
     */
    @Override
    public synchronized String take() {
        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
