package thread.control.sync.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV2 {
    // 비공정 모드 락
    private final Lock nonFailLock = new ReentrantLock();
    // 공정 모드 락
    private final Lock fairLock = new ReentrantLock(true);

    public static void main(String[] args) {


        Thread t1 = new Thread(new ParkTest(), "Thread-1");
        t1.start();

        // 잠시 대기하여 t1이 park 상태에 빠질 시간을 준다.
        sleep(100);
        log("Thread-1 state : " + t1.getState());
    }

    static class ParkTest implements Runnable {
        @Override
        public void run() {
            log("park 시작");
            LockSupport.parkNanos(2000_000000); // 2초 뒤 깨어남
            log("park 종료, state : " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
}
