package juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 基于AQS实现简单的不可重入的互斥锁，CLH队列自旋锁特性导致其为公平锁
 * @see java.util.concurrent.ThreadPoolExecutor.Worker
 * @author ctl
 * @date 2019/11/26
 */
public class MutexLock extends AbstractQueuedSynchronizer {
    private static final long serialVersionUID = 8447893012185455124L;

    /**
     * 尝试获取资源，立即返回。成功则返回true，否则false。
     * @param arg
     * @return
     */
    @Override
    protected boolean tryAcquire(int arg) {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    /**
     * 尝试释放资源，立即返回。成功则为true，否则false。
     * @param arg
     * @return
     */
    @Override
    protected boolean tryRelease(int arg) {
        if (getState() == 0) {
            throw new IllegalMonitorStateException();
        }
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public void lock() {
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        release(1);
    }

    public boolean isLocked() {
        return isHeldExclusively();
    }

    public static void main(String[] args) throws InterruptedException {
        final MutexLock lock = new MutexLock();
        lock.lock();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lock.lock();
                System.out.println(Thread.currentThread().getId() + " acquired the lock!");
                lock.unlock();
            }).start();
            // 简单的让线程按照for循环的顺序阻塞在lock上
            Thread.sleep(100);
        }

        System.out.println("main thread unlock!");
        lock.unlock();
    }
}
