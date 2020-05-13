package com.cdtelecom.redis.concurrent.locks.backupjuc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断
 * 构造一个死锁的例子，然后用中断来处理死锁
 * https://www.jianshu.com/p/155260c8af6c
 */
public class ReentrantLockInterrupt extends Thread {

    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    public ReentrantLockInterrupt(int lock, String name) {

        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                lock1.lockInterruptibly();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + ":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockInterrupt t1 = new ReentrantLockInterrupt(1, "LockInterrupt1");
        ReentrantLockInterrupt t2 = new ReentrantLockInterrupt(2, "LockInterrupt2");
        t1.start();
        t2.start();
        Thread.sleep(1000);

        DeadlockChecker.check();
    }


    //查找死锁并中断
    static class DeadlockChecker {

        private final static ThreadMXBean mbean = ManagementFactory
                .getThreadMXBean();

        public static void check() {

            Thread tt = new Thread(() -> {
                {
                    // TODO Auto-generated method stub
                    while (true) {
                        long[] deadlockedThreadIds = mbean.findDeadlockedThreads();
                        if (deadlockedThreadIds != null) {
                            ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
                            for (Thread t : Thread.getAllStackTraces().keySet()) {
//                                StackTraceElement[] ste = Thread.getAllStackTraces().get(t);
                                for (int i = 0; i < threadInfos.length; i++) {
                                    if (t.getId() == threadInfos[i].getThreadId()) {
                                        System.out.println("deadLockName:" + t.getName());
                                        t.interrupt();
                                    }
                                }
                            }
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                }
            });
            tt.setDaemon(true);
            tt.start();
        }

    }

}
