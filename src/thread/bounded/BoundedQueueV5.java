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
public class BoundedQueueV5 implements BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition producerCond = lock.newCondition();    // 스레드 대기 공간
    private final Condition consumerCond = lock.newCondition();    // 스레드 대기 공간

    /* Critical Section */
    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV5(int max) {
        this.max = max;
    }

    /**
     * 생산자 전용
     */
    @Override
    public void put(String data) {
        log("락 획득 시도");
        lock.lock();
        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참 " + data);
                try {
                    producerCond.await();
                    log("[put] 생산자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // offer은 예외 X false O
            queue.offer(data);
            log("[put] 생산자 데이터 저장, signal() 호출");
            consumerCond.signal(); // notify
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
        log("락 획득 시도");
        lock.lock();
        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터가 없음, 소비자 대기");
                try {
                    consumerCond.await();
                    log("[take] 소비자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            data = queue.poll();
            log("[take] 소비자 데이터 획득, signal() 호출");
            producerCond.signal();
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
