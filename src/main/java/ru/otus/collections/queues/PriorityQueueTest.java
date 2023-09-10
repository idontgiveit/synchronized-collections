package ru.otus.collections.queues;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class PriorityQueueTest {

    private final static int size = 1000;
    private static volatile boolean run = true;

    public static void main(String[] args) throws Exception{

        BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

        Random random = new Random();

        CyclicBarrier barrier = new CyclicBarrier(1);

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                waitOthers(barrier);
                for (int i = 0; i < size; i ++) {
                    queue.add((random.nextLong(1000)));
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                waitOthers(barrier);
                while (run) {

                    Long value = null;
                    try {
                        value = queue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                        print(value);
                }
            }
        });

        consumer.start();
        producer.start();

        producer.join();

        //run = false;

        consumer.join();


    }

    public static <T> void print(T item) {
        System.out.println(item);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitOthers(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
