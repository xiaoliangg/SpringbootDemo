package com.cdtelecom.redis.concurrent.locks.backupjuc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 自行测试ReentrantLock的非公平锁，但是表现是公平锁的效果？？？
 */
public class NonfairSynTest extends Thread {

    public static ReentrantLock lock = new ReentrantLock(false);

    public NonfairSynTest(String name) {
        super.setName(name);
    }

    @Override
    public void run() {
        //ReentrantLock的非公平锁，但是表现为公平锁
        lock.lock();
        System.out.println("start:" + this.getName());
        try {
            try{
            Thread.sleep(1000);
            }catch (Exception e){

            }
            System.out.println("end:" + this.getName());
        } finally {
            lock.unlock();
        }

        // synchronized 表现为非公平锁
//        synchronized (lock) {
//            System.out.println("start:" + this.getName());
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//
//            }
//            System.out.println("end:" + this.getName());
//        }


    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        NonfairSynTest t;
        for (int i = 0; i < 100; i++) {
            t = new NonfairSynTest("thread" + i);
            t.start();
        }
        System.out.println("main thread end");
    }
}
