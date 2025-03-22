package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;

/**
 * 역할 : 생산자 / 소비자의 Buffer 역할
 * 책임 : 생산자가 생산하는 인스턴스를 순서대로 잘 보관하는 책임
 */
public class BoundedQueueV4 implements BoundedQueue {

    /* Critical Section */
    private final Queue<String> queue = new ArrayDeque<>();
    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();    // 스레드 대기 공간
    private final int max;

    public BoundedQueueV4(int max) {
        this.max = max;
    }

    /**
     * 생산자 전용
     */
    @Override
    public void put(String data) {
        lock.lock();
        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참 " + data);
                try {
                    condition.await();
                    log("[put] 생산자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // offer은 예외 X false O
            queue.offer(data);
            condition.signal(); // notify
        }  finally {
            lock.unlock();
        }
    }

    /**
     * 소비자 전용
     */
    @Override
    public String take() {
        String data;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터가 없음, 소비자 대기");
                try {
                    condition.await();
                    log("[take] 소비자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            data = queue.poll();
            condition.signal();
        } finally {
            lock.unlock();
        }

        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
