package com.mcgrady.xproject.java.test;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by mcgrady on 2022/9/20.
 */
public class ThreadTest {

    public static void main(String[] args) {

        ThreadTest root = new ThreadTest();
//        root.threadPool();

        root.interrupt();
    }

    public void threadPool() {
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        ThreadPoolExecutor singleThreadExecutor = (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
        ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 15, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        threadPoolExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(5);
    }


    private volatile boolean isCancelled;

    public void interrupt() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                while(!isCancelled) {
//                    System.out.println("thread is running " + (System.currentTimeMillis() / 1000));
//                }

                while (Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println("thread is running " + System.currentTimeMillis());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        thread.start();
        try {
            Thread.sleep(200);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(1000);
//            isCancelled = true;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
