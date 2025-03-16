package thread.control.join;

import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinTest1Main {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add(new Thread(new MyTask(), "t" + i));
        }

        for(int i = 0; i < 100; i++) {
            list.get(i).start();
        }

        for(int i = 0; i < 100; i++) {
            list.get(i).join();
        }

        System.out.println("모든 스레드 실행 완료");
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                log(i);
                sleep(1000);
            }
        }
    }
}