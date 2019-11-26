package juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * 基于AQS实现的简单共享锁
 * @author ctl
 * @date 2019/11/26
 */
public class ShareLock {
    private Syn sync;

    public ShareLock(int count) { this.sync = new Syn(count); }

    public void lock() { sync.acquireShared(1); }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public boolean tryLock() { return sync.tryAcquireShared(1) >= 0; }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    public void unlock() { sync.releaseShared(1); }

    public Condition newCondition() { throw new UnsupportedOperationException(); }

    private static final class Syn extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 5854536238831876527L;
        Syn(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }

        @Override
        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                //如果新的状态小于0 则返回值，则表示没有锁资源，直接返回
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(int retrunCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + retrunCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }
}
