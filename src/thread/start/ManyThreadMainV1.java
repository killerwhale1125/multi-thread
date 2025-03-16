package thread.start;

import util.MyLogger;

import static util.MyLogger.*;

public class ManyThreadMainV1 {
    public static void main(String[] args) {
        log("main() start");

        // 같은 인스턴스로 세개의 스레드를 만들었다.
        HelloRunnable runnable = new HelloRunnable();
        for(int i = 0; i < 100; i ++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        log("main() end");
    }
}
