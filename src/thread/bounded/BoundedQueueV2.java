package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * 역할 : 생산자 / 소비자의 Buffer 역할
 * 책임 : 생산자가 생산하는 인스턴스를 순서대로 잘 보관하는 책임
 */
public class BoundedQueueV2 implements BoundedQueue {

    /* Critical Section */
    private Queue<String> queue = new ArrayDeque<>();
    private int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }

    /**
     * 생산자 전용
     */
    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] 큐가 가득 참 " + data);
            sleep(1000);
        }
        // offer은 예외 X false O
        queue.offer(data);
    }

    /**
     * 소비자 전용
     */
    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            sleep(1000);
        }

        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
