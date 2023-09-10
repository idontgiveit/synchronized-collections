package ru.otus.collections.queues;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class LinkedBlockingQueueTest1 {


    static AtomicLong available = new AtomicLong(5);

    static LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws Exception {

        long timeout = 3000;

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i ++) {
                    createConsumer(i);
                }

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

        Thread.sleep(100000);

    }


    public static void consume(Long item) {
        try {
            queue.put(item);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createConsumer(int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long value;
                    try {
                        value = queue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sleep(1000);
                    System.out.println("consumed by " + number + " " + value);
                }

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
