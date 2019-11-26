package juc.aqs;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH 锁和 MCS 锁区别主要有两点：1. 链表结构的区别；2. 自旋对象的区别，CLH 是在前驱节点上自旋，而 MCS 是在自身节点上自旋；
 * 这里第二点才是最重要的，主要体现在 SMP(Symmetric Multi-Processor) 和 NUMA(Non-Uniform Memory Access) 不同的处理器架构上；
 *
 * https://www.cnblogs.com/sanzao/p/10567529.html
 *
 * @author ctl
 * @date 2019/11/26
 */
public class McsSpinLock {
    private final ThreadLocal<Node> node = ThreadLocal.withInitial(Node::new);
    private final AtomicReference<Node> tail = new AtomicReference<>();

    private static class Node {
        private volatile boolean locked = false;
        private volatile Node next = null;
    }

    public void lock() {
        Node node = this.node.get();
        node.locked = true;
        Node pre = tail.getAndSet(node);
        if (pre != null) {
            pre.next = node;
            while (node.locked);
        }
    }

    public void unlock() {
        Node node = this.node.get();
        if (node.next == null) {
            if (tail.compareAndSet(node, null)) {
                return;
            }
            while (node.next == null);
        }
        node.next.locked = false;
        node.next = null;
    }

    public static void main(String[] args) throws InterruptedException {
        final McsSpinLock lock = new McsSpinLock();
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
