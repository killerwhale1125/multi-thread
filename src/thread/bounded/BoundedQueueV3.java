package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 역할 : 생산자 / 소비자의 Buffer 역할
 * 책임 : 생산자가 생산하는 인스턴스를 순서대로 잘 보관하는 책임
 */
public class BoundedQueueV3 implements BoundedQueue {

    /* Critical Section */
    private Queue<String> queue = new ArrayDeque<>();
    private int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    /**
     * 생산자 전용
     */
    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참 " + data);
            try {
                wait(); // RUNNABLE -> WAITING 및 모니터 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // offer은 예외 X false O
        queue.offer(data);
        log("[put] 생산자 데이터 저장, notify() 호출");
        notify();   // 대기 스레드, WAIT -> BLOCKED
    }

    /**
     * 소비자 전용
     */
    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait();
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify();   // 대기 스레드, WAIT -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
