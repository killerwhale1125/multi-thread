package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV4 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread work = new Thread(task, "work");
        work.start();

        sleep(100);
        log("작업 중단 지시 Thread Interrupt");
        work.interrupt();
        /* work Thread를 인터럽트 상태로 변경시킨다. */
        log("work Thread 인터럽트 상태 : " + work.isInterrupted() + "\n");
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            /* 인터럽트 발생 후 while 문 탈출 시 true -> false 변경 */
            while(!Thread.interrupted()) {
                log("작업 중");
            }

            log("인터럽트 발생!! : " + Thread.currentThread().isInterrupted());
            log("인터럽트 상태 : " + Thread.currentThread().getState() + "\n");

            log("자원 정리");
            log("작업 종료");
        }
    }
}
