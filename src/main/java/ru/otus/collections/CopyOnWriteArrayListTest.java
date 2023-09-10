package ru.otus.collections;

import ru.otus.collections.util.SimpleTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

public class CopyOnWriteArrayListTest {

    public static final int size = 10000;

    static AtomicLong ctr = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        List<Double> testList = new CopyOnWriteArrayList<>();
        Random random = new Random();

        CyclicBarrier barrier = new CyclicBarrier(3);

        SimpleTimer.start();

        Thread appender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < size; i ++) {
                    insert(testList, random.nextDouble());
                }
            }
        });


        Thread appender1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < size; i ++) {
                    insert(testList, random.nextDouble());
                }
            }
        });

        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
//                testList.forEach(CopyOnWriteArrayListTest::print);
                for (int j = 0; j < 3; j ++) {
                    sleep(10);
                    ctr.set(0);
                for (Double item : testList) {
                    print(item);
                }
            }}
        });

        appender.start();
        appender1.start();
        reader.start();

        reader.join();
        appender1.join();
        appender.join();

        System.out.println(testList.size());

        SimpleTimer.print();


    }

    public static <T> void insert(List<T> list, T item) {
        list.add(item);
    }

    public static <T> void print(T item) {
        System.out.println(ctr.incrementAndGet() + " " + item);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
