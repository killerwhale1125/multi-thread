package thread.control.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV5 implements BankAccount {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV5(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

        /* 대기조차 안하고 바로 종료 */
        if(!lock.tryLock()) {
            log("[진입 실패] 이미 처리중인 작업이 있습니다.");
            return false;
        }

        try {
            log("[검증 시작] 출금액 : " + amount + ", 잔액 : " + balance);
            if(balance < amount) {
                log("[검증 실패] 출금액 : " + amount + ", 잔액 : " + balance);
                return false;
            }

            log("[검증 성공] 출금액 : " + amount + ", 잔액 : " + balance);
            sleep(1000);    // 출금이 걸리는 시간
            balance = balance - amount;
            log("[출금 완료] 출금액 : " + amount + ", 잔액 : " + balance);
        } finally {
            lock.unlock();  // lock 해제
        }

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();

        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
