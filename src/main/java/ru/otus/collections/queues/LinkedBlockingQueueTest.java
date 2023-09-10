package ru.otus.collections.queues;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class LinkedBlockingQueueTest {


    static AtomicLong available = new AtomicLong(5);

    static Object monitor = new Object();

    public static void main(String[] args) throws Exception {

        long timeout = 5000;

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                for (int i = 0; i < 50; i ++) {
                    consume((long)i);
                    if (System.currentTimeMillis() - time > timeout) {
                        throw new IllegalStateException("timeout");
                    }
                }
            }
        });

        producer.start();
        producer.join();

    }

    public static void consume(Long item) {
        while (available.intValue() < 1) {
            Thread.yield();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                available.decrementAndGet();
                sleep(1000);
                System.out.println("consumed " + item);
                available.incrementAndGet();
            }
        }).start();
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
