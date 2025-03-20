package thread.control.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ParkTest(), "Thread-1");
        t1.start();

        // 잠시 대기하여 t1이 park 상태에 빠질 시간을 준다.
        sleep(100);

        log("Thread-1 state : " + t1.getState());

        log("main -> unpark(Thread-1)");
//        LockSupport.unpark(t1); // Thread를 깨운다 ( unpark )
        t1.interrupt(); // Thread를 깨운다 ( interrupt )

    }

    static class ParkTest implements Runnable {
        @Override
        public void run() {
            log("park 시작");
            LockSupport.park(); // 이 코드를 호출한 Thread는 RUNNABLE -> WAITING 상태로 변환
            log("park 종료, state : " + Thread.currentThread().getState());
            log("인터럽트 상태 : " + Thread.currentThread().isInterrupted());
        }
    }
}
