package com.testdemo.mulitthread;

import java.util.PriorityQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 18 16:10
 * @DESC：
 */

public class Test {
    int maxSize = 5;
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ThreadA threadA = new ThreadA(countDownLatch);
        ThreadB threadB = new ThreadB(countDownLatch);
        System.out.println("=======大家开始执行任务吧======");
        threadA.start();
        threadB.start();
        countDownLatch.await();
        System.out.println("=======收到，全部执行完毕=======");
    }

    static class ThreadA extends Thread{
        private CountDownLatch countDownLatch;
        public ThreadA(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("=========报告，A执行完毕");
            /*while (true){
                synchronized(queue){
                    if (queue.size() == 0) return;
                    *//*if (queue.size() == 0) {
                        System.out.println("===========没有数据");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*//*
                    queue.poll();//去除一个数据
                    //queue.notify();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                }
            }*/
        }
    }

    static class ThreadB extends Thread{

        private CountDownLatch countDownLatch;
        public ThreadB(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            countDownLatch.countDown();
            System.out.println("=========报告，B执行完毕");
            /*while (true){
                synchronized(queue) {
                    *//*if (queue.size() == maxSize) {
                        System.out.println("====================已经装不下了");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }*//*
                    if (queue.size() < maxSize){
                        queue.offer(1);
                    }
                    //queue.notify();
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("向队列添加一个元素，队列共" + queue.size() + "个元素");
                }
            }*/
        }
    }
}
