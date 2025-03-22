package thread.bounded;

import java.util.LinkedList;
import java.util.Queue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        JobQueue<Integer> jobQueue = new JobQueue<>();

        Thread t1 = new Thread(new Task(jobQueue), "Thread-1");
        Thread t2 = new Thread(new Task2(jobQueue), "Thread-2");

        t1.start();
        t2.start();

        Thread.sleep(100);
        System.out.println(t1.getState());
        System.out.println(t2.getState());

        t1.join();
        t2.join();

        System.out.println("종료");
    }

    static class JobQueue<T> {
        private Queue<T> q = new LinkedList<>();

        public synchronized boolean add(T t) {
            log(Thread.currentThread().getName() + "접근");
            q.offer(t);
            while(!q.isEmpty()) {
                sleep(10000);
            }
            log(Thread.currentThread().getName() + "탈출");
            return true;
        }

        public T poll() {
            System.out.println("poll");
            return q.poll();
        }
    }

    static class Task implements Runnable {

        private JobQueue jobQueue;

        public <T> Task(JobQueue<T> jobQueue) {
            this.jobQueue = jobQueue;
        }

        @Override
        public void run() {
            jobQueue.add(1);
        }
    }

    static class Task2 implements Runnable {

        private JobQueue jobQueue;

        public <T extends Number> Task2(JobQueue<T> jobQueue) {
            this.jobQueue = jobQueue;
        }

        @Override
        public void run() {
            jobQueue.poll();
        }
    }
}
