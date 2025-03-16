package test.test1;

public class CounterRunnableMain {
    public static void main(String[] args) {
        CounterRunnable runnable = new CounterRunnable();
        Thread thread = new Thread(runnable);
        thread.setName("counter");
        thread.start();
    }
}
