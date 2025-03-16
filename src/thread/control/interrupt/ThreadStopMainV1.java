package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV1 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread work = new Thread(task, "work");
        work.start();

        sleep(4000);
        log("작업 중단 지시 Thread Interrupt");
        work.interrupt();
        /* work Thread를 인터럽트 상태로 변경시킨다. */
        log("work Thread 인터럽트 상태 : " + work.isInterrupted() + "\n");
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                while(true) {
                    log("작업 중");
                    Thread.sleep(3000);
                }
            }
            catch (InterruptedException e) {
                /* 인터럽트 상태가 되어 InterruptedException 예외가 발생하였으며 Runnable 상태로 변경하여 인터럽트 상태는 false */
                log("인터럽트 발생!! : " + Thread.currentThread().isInterrupted());
                log("인터럽트 메세지 : " + e.getMessage());
                log("인터럽트 상태 : " + Thread.currentThread().getState() + "\n");
            }

            log("자원 정리");
            log("작업 종료");
        }
    }
}
