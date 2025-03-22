package thread.bounded;

import static util.MyLogger.log;

/**
 * 역할 : 소비자에게 요청을 전달
 * 책임 : 소비자가 잘 전달받기 위해 Buffer 에 요청을 Put
 */
public class ProducerTask implements Runnable {

    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }

    @Override
    public void run() {
        log("[생산 시도] " + request + " -> " + queue);
        queue.put(request);
        log("[생산 완료] " + request + " -> " + queue);
    }
}
