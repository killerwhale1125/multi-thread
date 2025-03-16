package test.test1;

import util.MyLogger;

public class CounterRunnable implements Runnable{
    @Override
    public void run() {
        for(int i = 1; i <= 5; i++) {
            MyLogger.log("value: " + i);
        }
    }
}
