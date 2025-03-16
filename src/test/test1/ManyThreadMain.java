package test.test1;

import util.MyLogger;

import static util.MyLogger.*;

public class ManyThreadMain {
    public static void main(String[] args) {
        Thread threadA = new ThreadA();
        Thread threadB = new ThreadB();
        threadA.start();
        threadB.start();
    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    log("A");
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    log("B");
                    sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
