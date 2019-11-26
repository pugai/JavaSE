package juc.aqs;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH队列锁（自旋锁）
 * 通过ThreadLocal保存了当前结点和前继结点的引用，自旋就是lock中的while循环。
 * 这种实现的好处是保证所有等待线程的公平竞争，而且没有竞争同一个变量，因为每个线程只要等待自己的前继释放就好了。
 * 而自旋的好处是线程不需要睡眠和唤醒，减小了系统调用的开销。
 *
 * ClhSpinLock的Node类实现很简单只有一个布尔值，AbstractQueuedSynchronizer$Node的实现复杂点，大概是这样的：
 *      +------+  prev +-----+       +-----+
 * head |      | <---- |     | <---- |     |  tail
 *      +------+       +-----+       +-----+
 * head：头指针
 * tail：尾指针
 * prev：指向前继的指针
 * next：这个指针图中没有画出来，它跟prev相反，指向后继
 * 关键不同就是next指针，这是因为AQS中线程不是一直在自旋的，而可能会反复的睡眠和唤醒，这就需要前继释放锁的时候通过next 指针找到其后继将其唤醒，
 * 也就是AQS的等待队列中后继是被前继唤醒的。AQS结合了自旋和睡眠/唤醒两种方法的优点。
 *
 * http://zhanjindong.com/2015/03/11/java-concurrent-package-aqs-clh-and-spin-lock
 *
 * @author ctl
 * @date 2019/11/26
 */
public class ClhSpinLock {
    private ThreadLocal<Node> prev = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    /**
     * tail最初指向一个伪节点，改节点locked默认为false
     */
    private AtomicReference<Node> tail = new AtomicReference<>(new Node());

    public void lock() {
        Node node = this.node.get();
        node.locked = true;
        // 一个CAS操作即可将当前线程对应的节点加入到队列中，并且同时获得了前继节点的引用，然后就是等待前继释放锁
        Node pred = tail.getAndSet(node);
        this.prev.set(pred);
        // 自旋
        while (pred.locked);
    }

    public void unlock() {
        Node node = this.node.get();
        node.locked = false;
        this.node.set(this.prev.get());
    }

    private static class Node {
        private volatile boolean locked;
    }

    public static void main(String[] args) throws InterruptedException {
        final ClhSpinLock lock = new ClhSpinLock();
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
